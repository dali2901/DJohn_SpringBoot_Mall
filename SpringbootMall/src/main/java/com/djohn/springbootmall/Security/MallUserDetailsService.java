package com.djohn.springbootmall.Security;

import com.djohn.springbootmall.Dao.MemberDao;
import com.djohn.springbootmall.Model.Member;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MallUserDetailsService implements UserDetailsService {
  private final MemberDao memberDao;
    public MallUserDetailsService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

    //從資料庫中 查詢member數據
    Member member = memberDao.getMemberByEmail(userName);

    if(member == null){
      throw new UsernameNotFoundException("User Name Not Found :" + userName);
    }else {
      String memberEmail = member.getEmail();
      String memberPassword = member.getPassword();

      //權限部分 先不用管
      List<GrantedAuthority> authorities = new ArrayList<>();

      //轉換成spring security 指定的 user 格式
      return new User(memberEmail, memberPassword, authorities);
    }


  }
}
