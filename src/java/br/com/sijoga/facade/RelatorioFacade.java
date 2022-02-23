package br.com.sijoga.facade;

import br.com.sijoga.dao.RelatorioDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.exception.RelatorioException;
import br.com.sijoga.mb.LoginMb;
import br.com.sijoga.util.SijogaUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class RelatorioFacade {

    private static final RelatorioDao relatorioDao = new RelatorioDao();
    private static final String host = "http://localhost:8080/sijoga/";

    public static byte[] relatorioProcessosAbertos(Date dataInicial, Date dataFinal) throws RelatorioException {
        try {
            if ((dataInicial == null)) {
                throw new RelatorioException("Data inicial inválida");
            } else if ((dataFinal == null) || (dataFinal.before(dataInicial))) {
                throw new RelatorioException("Data de saída não pode ser anterior a data de entrada");
            } else {
                FacesContext ctx = FacesContext.getCurrentInstance();
                Application app = ctx.getApplication();
                LoginMb usuario = app.evaluateExpressionGet(ctx, "#{loginMb}", LoginMb.class);

                String jasper = "Relatorios/ProcessosEmAberto.jasper";
                URL jasperURL = new URL(host + jasper);
                HashMap params = new HashMap();
                params.put("dataInicio", dataInicial);
                params.put("dataFim", dataFinal);
                params.put("idAdvPromovente", usuario.getAdvogado().getId());
                params.put("advPromovente", usuario.getAdvogado().getNome());
                return JasperRunManager.runReportToPdf(jasperURL.openStream(),
                        params,
                        connectionHibernate());
            }
        } catch (IOException | JRException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao gerar relatório";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        } finally {
            finalizaSessaoHibernate();
        }
    }

    public static byte[] relatorioProcessosEncerrados(Date dataInicial, Date dataFinal) throws RelatorioException {
        try {
            if ((dataInicial == null)) {
                throw new RelatorioException("Data inicial inválida");
            } else if ((dataFinal == null) || (dataFinal.before(dataInicial))) {
                throw new RelatorioException("Data de saída não pode ser anterior a data de entrada");
            } else {
                FacesContext ctx = FacesContext.getCurrentInstance();
                Application app = ctx.getApplication();
                LoginMb usuario = app.evaluateExpressionGet(ctx, "#{loginMb}", LoginMb.class);

                String jasper = "Relatorios/ProcessosEncerrados.jasper";
                URL jasperURL = new URL(host + jasper);
                HashMap params = new HashMap();
                params.put("dataInicio", dataInicial);
                params.put("dataFim", dataFinal);
                params.put("idAdvPromovente", usuario.getAdvogado().getId());
                params.put("advPromovente", usuario.getAdvogado().getNome());
                return JasperRunManager.runReportToPdf(jasperURL.openStream(),
                        params,
                        connectionHibernate());
            }
        } catch (IOException | JRException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao gerar relatório";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        } finally {
            finalizaSessaoHibernate();
        }
    }

    public static byte[] relatorioParte() throws RelatorioException {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            Application app = ctx.getApplication();
            LoginMb usuario = app.evaluateExpressionGet(ctx, "#{loginMb}", LoginMb.class);

            String jasper = "Relatorios/ProcessosParte.jasper";
            URL jasperURL = new URL(host + jasper);
            HashMap params = new HashMap();
            params.put("idParte", usuario.getParte().getId());
            return JasperRunManager.runReportToPdf(jasperURL.openStream(),
                    params,
                    connectionHibernate());
        } catch (IOException | JRException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao gerar relatório";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        } finally {
            finalizaSessaoHibernate();
        }
    }

    private static Connection connectionHibernate() {
        try {
            return relatorioDao.connectionHibernate();
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema de conexão com o banco de dados";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    private static void finalizaSessaoHibernate() {
        try {
            relatorioDao.finalizaSessaoHibernate();
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao finalizar conexão com o banco de dados";
            SijogaUtil.mensagemErroRedirecionamento(msg);
        }
    }

}
