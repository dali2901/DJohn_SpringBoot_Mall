package com.djohn.springbootmall.Controller;

import com.djohn.springbootmall.Dao.MemberDao;
import com.djohn.springbootmall.Model.Member;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  private final MemberDao memberDao;
  private final PasswordEncoder passwordEncoder;

  public MemberController(MemberDao memberDao, PasswordEncoder passwordEncoder) {
    this.memberDao = memberDao;
    this.passwordEncoder =  passwordEncoder;
  }

  @PostMapping("/register")
  public Member register(@RequestBody Member member){

    //Hash原始密碼
    String hashedPassword = passwordEncoder.encode(member.getPassword());
    member.setPassword(hashedPassword);

    //省略了參數的檢查 ex; (email是否被註冊過)
    //在DB插入member數據
    Integer memberId = memberDao.createMember(member);

    return memberDao.getMemberById(memberId);
  }

  @GetMapping("/login")
  public String login(Authentication authentication){

    //取得使用者的帳號
    String username = authentication.getName();

    //取得使用者的權限
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    return "Hello" + username + ", Welcome to Mall";
  }
}
