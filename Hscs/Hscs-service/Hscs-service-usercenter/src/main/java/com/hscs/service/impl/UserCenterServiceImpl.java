package com.hscs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hscs.commonutils.JwtUtils;
import com.hscs.commonutils.MD5;
import com.hscs.entity.UserCenter;
import com.hscs.entity.vo.RegisterVO;
import com.hscs.mapper.UserCenterMapper;
import com.hscs.service.UserCenterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hscs.servicebase.exceptionhandler.HscsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-03-04
 */
@Service
public class UserCenterServiceImpl extends ServiceImpl<UserCenterMapper, UserCenter> implements UserCenterService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**登录方法*/
    @Override
    public String login(UserCenter user) {
        //获取登录手机号和密码
        String mobile = user.getMobile();
        String password = user.getPassword();

        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new HscsException(20001,"登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<UserCenter> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UserCenter mobileuser = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(mobileuser == null) {//没有这个手机号
            throw new HscsException(20001,"登录失败");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(mobileuser.getPassword())) {
            throw new HscsException(20001,"登录失败");
        }

        //判断用户是否禁用
        if(mobileuser.getIsDisabled()) {
            throw new HscsException(20001,"登录失败");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileuser.getId(), mobileuser.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVO registerVO) {
        //获取注册的数据
        //   验证码   手机号  昵称  密码
        String code = registerVO.getCode();
        String mobile = registerVO.getMobile();
        String nickname = registerVO.getNickname();
        String password = registerVO.getPassword();

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new HscsException(20001,"注册失败");
        }
        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)) {
            throw new HscsException(20001,"注册失败");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UserCenter> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new HscsException(20001,"注册失败");
        }

        //数据添加数据库中
        UserCenter user = new UserCenter();
        user.setMobile(mobile);
        user.setNickname(nickname);
        //密码需要加密的
        user.setPassword(MD5.encrypt(password));
        //用户不禁用
        user.setIsDisabled(false);
        user.setAvatar("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3401732207,3726302783&fm=26&gp=0.jpg");
        baseMapper.insert(user);
    }

    /**根据openid判断*/
    @Override
    public UserCenter getOpenIdUser(String openid) {
        QueryWrapper<UserCenter> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UserCenter user = baseMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
