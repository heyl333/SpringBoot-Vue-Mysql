package com.example.demo.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;

    // 用户新增
    @PostMapping
    public Result<?> save (@RequestBody User user) {
        userMapper.insert(user);
        return Result.success();
    }

    // 用户更新
    @PutMapping
    public Result<?> put (@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public Result<?> delete (@PathVariable Long id) {
        userMapper.deleteById(id);
        return Result.success();
    }

    // 用户查询
    @GetMapping
    public Result<?> findPage (@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String searchUsername,
                               @RequestParam(defaultValue = "") String searchNickname) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (StringUtils.isNotBlank(searchUsername)) {
            wrapper.like(User::getUsername,searchUsername);
        }
        if (StringUtils.isNotBlank(searchNickname)) {
            wrapper.like(User::getNickName,searchNickname);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }

    // 用户登录
    @PostMapping("/login")
    public Result<?> login (@RequestBody User user) {
        User login_user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if ( login_user == null) {
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success();
    }
}
