package com.ktg.usuarioSpring.security;

import com.ktg.usuarioSpring.dao.IUsuarioDao;
import com.ktg.usuarioSpring.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    IUsuarioDao usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.getUsuarioLogin(s);
        User.UserBuilder builder = null;

        if (usuario != null){
            builder = User.withUsername(s);
            builder.disabled(false);
            builder.password(usuario.getPassword());
            builder.authorities(new SimpleGrantedAuthority("user"));
        }else{
            return null;
        }
        return builder.build();
    }
}
