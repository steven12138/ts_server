package org.twt.ts.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.twt.ts.dto.BasicUserInfo;
import org.twt.ts.dto.PasswordPair;
import org.twt.ts.dto.RegisterUser;
import org.twt.ts.dto.User;
import org.twt.ts.exception.*;
import org.twt.ts.model.Account;
import org.twt.ts.model.repository.AccountRepo;
import org.twt.ts.service.AuthService;
import org.twt.ts.utils.JwtUtil;
import org.twt.ts.utils.UserInfoUtil;

import javax.annotation.Resource;

@Service("asi")
public class AuthServiceImpl implements AuthService {

    @Resource
    private AccountRepo accountRepo;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private UserInfoUtil userInfoUtil;

    @Override
    public BasicUserInfo login(User user) throws UsernamePasswordNotMatchException, UserForbiddenException {
        Account target = accountRepo.findAccountByUsername(user.getUsername())
                .orElseThrow(UsernamePasswordNotMatchException::new);
        if (!passwordEncoder.matches(user.getPassword(), target.getPassword()))
            throw new UsernamePasswordNotMatchException();

        if (target.isForbidden()) throw new UserForbiddenException();
        return new BasicUserInfo(
                target.getUsername(),
                target.getNickname(),
                jwtUtil.generateToken(target)
        );
    }


    @Override
    public void register(RegisterUser registerUser) throws UsernameExistException {
        Account user = new Account();
        BeanUtils.copyProperties(registerUser, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            accountRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameExistException();
        }
    }

    private Account getCurrentUser() throws NoPrivilegesException {
        int id = userInfoUtil.getUserId();
        return accountRepo.findAccountById(id)
                .orElseThrow(NoPrivilegesException::new);
    }

    @Override
    public void modifyPassword(PasswordPair passwordPair) throws PasswordNotMatchException, NoPrivilegesException {
        Account target = getCurrentUser();

        if (!passwordEncoder.matches(passwordPair.getOldPassword(), target.getPassword()))
            throw new PasswordNotMatchException();

        target.setPassword(passwordEncoder.encode(passwordPair.getNewPassword()));
        accountRepo.save(target);
    }

    @Override
    public void modifyPassword(String securityAnswer, String newPassword) throws SecurityAnswerException, NoPrivilegesException {
        Account target = getCurrentUser();

        if (!securityAnswer.equals(target.getSecurityAnswer())) throw new SecurityAnswerException();

        target.setPassword(passwordEncoder.encode(newPassword));

        accountRepo.save(target);
    }

    @Override
    public String getSecurityQuestion() throws NoPrivilegesException {
        Account target = getCurrentUser();
        return target.getSecurityQuestion();
    }


}
