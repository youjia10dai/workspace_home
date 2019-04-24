package main.study.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroHelper implements ShiroHelperI {

    public static Logger log = Logger.getLogger(ShiroHelper.class);
    
    private Subject subject;
    private Session session;
    private static Factory<SecurityManager> factory;
    private static SecurityManager securityManager;
    
    static{
        factory = new IniSecurityManagerFactory("classpath:main/study/shiro/ini/mul-realm-shiro.ini");
        securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
    }
    
    public ShiroHelper(){
        init();
    }
    
    private void init(){
        subject = SecurityUtils.getSubject();
        
    }

    public boolean login(String name, String password){
        try {
            return toLogin(name, password);
        }catch (UnknownAccountException uae) {
            throw new ShiroException("账户不存在");
        } catch (IncorrectCredentialsException ice) {
            throw new ShiroException("密码不正确");
        } catch (LockedAccountException lae) {
            throw new ShiroException("用户被锁定了 ");
        } catch (AuthenticationException ae) {
            throw new ShiroException("未知异常" + ae.getMessage());
        }
    }
    
    private boolean toLogin(String name, String password){
        if (subject.isAuthenticated()) {
            return true;
        }
        UsernamePasswordToken tolen = getToken(name, password, true);
        subject.login(tolen);
        return true;
    }
    
    private UsernamePasswordToken getToken(String name, String password, boolean isRemember){
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        token.setRememberMe(isRemember);
        return token;
    }
    
    public void setAttribute(Object key, Object value){
        if(session == null){
            session = subject.getSession();
        }
        session.setAttribute(key, value);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(Object key){
        if(session == null){
            session = subject.getSession();
        }
        return (T)session.getAttribute(key);
    }
    
    public String getUserName(){
        return (String)subject.getPrincipal();
    }
    
    public boolean hasRole(String roleName){
        return subject.hasRole(roleName);
    }
    
    public boolean hasPermitted(String permittedName){
        return subject.isPermitted(permittedName);
    }
    
    public void logout(){
        subject.logout();
    }
}