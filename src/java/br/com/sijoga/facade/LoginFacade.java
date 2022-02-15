/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sijoga.facade;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.bean.Juiz;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.dao.LoginDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.Seguranca;

public class LoginFacade {
    private static final LoginDao loginDao = new LoginDao();

    public static Advogado loginAdvogado(Advogado advogado) throws DaoException {
        try {
            if (!Seguranca.isEmail(advogado.getEmail()) || advogado.getSenha() == null) {
                return null;                
            } else {
                return loginDao.loginAdvogado(advogado);                
            }            
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao efetuar login de advogado [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }    
    
    public static Juiz loginJuiz(Juiz juiz) throws DaoException {
        try {
            if (!Seguranca.isEmail(juiz.getEmail()) || juiz.getSenha() == null) {
                return null;                
            } else {
                return loginDao.loginJuiz(juiz);                
            }            
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao efetuar login de juiz [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }    
    
    public static Parte loginParte(Parte parte) throws DaoException {
        try {
            if (!Seguranca.isEmail(parte.getEmail()) || parte.getSenha() == null) {
                return null;                
            } else {
                return loginDao.loginParte(parte);                
            }            
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao efetuar login de parte [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }    
}
