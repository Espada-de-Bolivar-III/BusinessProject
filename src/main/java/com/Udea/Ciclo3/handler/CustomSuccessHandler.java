package com.Udea.Ciclo3.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler { //la clase custom es una subclase de la familia SimpleURL por eso extiende

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    protected void handle (HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String targetUrl = determineTargetUrl (authentication); //LLamo al metodo que me da la url donde debo ir en la web
        if (response.isCommitted()){
            System.out.println("No se puede redireccionar");
            return;
        }
        redirectStrategy.sendRedirect(request,response,targetUrl);
    }


    //metodo para verificar que rol tiene la persona y generar el URL --

    protected  String determineTargetUrl(Authentication authentication){
        String url="";
        //verificar de forma automatica los autorities que viene por defecto en spring Boot

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        //añadimos una lista de roles
        List<String> roles = new ArrayList<String>();

        //Cada objeto que encuentre en autoritie la asuma como un rol
        for (GrantedAuthority a: authorities){
            roles.add(a.getAuthority());
        }
        if (esAdministrativo(roles)){
            url="/ViewEnterprises";
        }
        else if(esOperativo(roles)) {
            url = "/ViewTransactions";
        }
        else {
            url="/Denegado";
        }
        return url;
    }

    private boolean esOperativo(List<String>roles){
        if (roles.contains("ROLE_USER")){
            return true;
        }
        return false;
    }

    private boolean esAdministrativo(List<String>roles){
        if (roles.contains("ROLE_ADMIN")){
            return true;
        }
        return false;
    }
}
