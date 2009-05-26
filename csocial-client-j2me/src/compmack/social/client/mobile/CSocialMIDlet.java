/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compmack.social.client.mobile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import org.kxml2.io.KXmlParser;
import org.netbeans.microedition.lcdui.LoginScreen;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class ExecutaSolicitacao implements Runnable
{
    final static int USUARIO = 1;
    final static int LISTA_RECADOS = 2;
    final static int RECADO = 3;

    CSocialMIDlet midlet;
    Displayable displayableSucesso;
    Displayable displayableFalha;
    String url;
    String[] parametros;
    String[] conteudos;
    int tipoConteudo;

    public ExecutaSolicitacao(CSocialMIDlet midlet, 
            Displayable displayableSucesso,
            Displayable displayableFalha,
            String url,
            int tipoConteudo,
            String[] parametros,
            String[] conteudos)
    {
        this.midlet = midlet;
        this.displayableSucesso = displayableSucesso;
        this.displayableFalha = displayableFalha;
        this.url = url;
        this.parametros = parametros;
        this.conteudos = conteudos;
        this.tipoConteudo = tipoConteudo;
    }

    public void run()
    {
        String urlChamada = "http://localhost:8080/csocial/ws/" + url;
        //String urlChamada = "http://localhost:9999/csocial/ws/" + url;
        boolean sucesso;

        sucesso = executaChamada(urlChamada, tipoConteudo, parametros, conteudos);

        // Se for sucesso ou falha escolhe o displayble correto...
        midlet.switchDisplayable(null, 
                sucesso ? displayableSucesso: displayableFalha);

    }

    private boolean executaChamada(String url, int tipoConteudo, String[] parametros, String[] conteudos)
    {
        XmlPullParser parser = new KXmlParser();
        byte[] conteudo = null;
        boolean sucesso = false;

        try
        {
            conteudo = recuperaConteudo(url, parametros, conteudos);

            String x = new String(conteudo);
            //StringStre

            parser.setInput(new InputStreamReader(new ByteArrayInputStream(conteudo)));

            String tag, resultado;

            // Recupera a primeira tag (o nome do objeto)
            parser.nextTag();
            // Recupera o sucesso/falha da chamada
            parser.nextTag();

            tag = parser.getName();

            if ( tag.equals("sucesso") )
            {
                resultado = parser.nextText();

                if ( resultado.equals("true") )
                    sucesso = true;
            }

            // Extrai os objetos
            if ( sucesso )
            {
                switch(tipoConteudo)
                {
                    case USUARIO:
                        midlet.setUsuario(recuperaUsuario(parser));
                        break;
                    case LISTA_RECADOS:
                        midlet.setMensagens(recuperaRecados(parser));
                }
            }
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
        catch(XmlPullParserException e)
        {
            System.out.println(e.getMessage());
        }

        return sucesso;
    }

    mensagem[] recuperaRecados(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        Vector lista = new Vector();
        mensagem m;
        mensagem[] retorno;

        do
        {
            m = recuperaRecado(parser);

            if ( m != null )
                lista.addElement(m);

        } while ( m != null );

        retorno = new mensagem[lista.size()];
        lista.copyInto(retorno);

        return retorno;
    }

    mensagem recuperaRecado(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        mensagem m = null;
        int valor;

        do {
            valor = parser.nextTag();

            if ( parser.getName().equals("resultado") )
                return null;
            
        } while ( valor == parser.END_TAG);

        if ( valor == parser.END_DOCUMENT )
            return null;

        if ( parser.getName().equals("message") )
        {
            m = new mensagem();

            for (int i = 0 ; i < parser.getAttributeCount(); i++)
            {
                String nome = parser.getAttributeName(i),
                        conteudo = parser.getAttributeValue(i);

                if ( nome.equals("status") )
                    m.status = conteudo;
                else if ( nome.equals("postDate") )
                    m.postDate = conteudo;
                else if ( nome.equals("id") )
                    m.id = Integer.parseInt(conteudo);
            }

            m.author = recuperaUsuario(parser);
            m.owner = recuperaUsuario(parser);

            do {
                valor = parser.nextTag();
            } while ( valor == parser.END_TAG);

            if ( parser.getName().equals("subject") )
                m.subject = parser.nextText();

            do {
                valor = parser.nextTag();
            } while ( valor == parser.END_TAG);

            if ( parser.getName().equals("text") )
                m.text = parser.nextText();
        }

        return m;
    }

    usuario recuperaUsuario(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        usuario u = null;
        int valor;

        do
        {
            valor = parser.nextTag();
        } while ( valor == parser.END_TAG );

        //if ( parser.getName().equals("usuario") )
        if ( valor == parser.START_TAG )
        {
            u = new usuario();

            for (int i = 0 ; i < parser.getAttributeCount(); i++)
            {
                String nome = parser.getAttributeName(i),
                        conteudo = parser.getAttributeValue(i);

                if ( nome.equals("username") )
                    u.username = conteudo;
                else if ( nome.equals("realName") )
                    u.realName = conteudo;
                else if ( nome.equals("nickName") )
                    u.nickName = conteudo;
                else if ( nome.equals("id") )
                    u.id = Integer.parseInt(conteudo);
                else if ( nome.equals("email") )
                    u.email = conteudo;
            }
        }

        return u;
    }

    /**
     * Executa a chamada http e devolve o array de bytes com o conteúdo
     * @param url Url a recuperar
     * @param parametros Parâmetros a serem enviados pela solicitação
     * @return dados recebidos, ou null se houve algum erro
     */
    private byte[] recuperaConteudo(String url, String[] parametros, String[] conteudos)
    {
        HttpConnection connection = null;
        InputStream inputstream = null;
        byte[] retorno = null/*, dadosEnvio*/;
        String solicitacao = "";
        String cookie;

        try
        {
            if ( parametros != null && conteudos != null )
            {
                for(int i = 0 ; i < parametros.length; i++ )
                {
                    solicitacao += parametros[i] + "=" + conteudos[i];

                    if ( i != parametros.length - 1 )
                        solicitacao += "&";
                }
            }
            //dadosEnvio = solicitacao.getBytes();

            url = url + "?" + solicitacao;
            connection = (HttpConnection) Connector.open(url);
            cookie = midlet.getCookie();

            if ( cookie != null )
                connection.setRequestProperty("Cookie", cookie);

            /*connection.setRequestMethod(HttpConnection.POST);
            connection.setRequestProperty("Content-Length", "" + dadosEnvio.length);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "close");

            OutputStream os = connection.openOutputStream();

            os.write(dadosEnvio);
            os.flush();
            os.close();*/

            int retornoHttp = connection.getResponseCode();

            if (retornoHttp == HttpConnection.HTTP_OK)
            {
                if ( cookie == null )
                {
                    cookie = connection.getHeaderField("Set-Cookie");
                    midlet.setCookie(cookie);
                }

                inputstream = connection.openInputStream();

                int length = (int) connection.getLength();

                if (length != -1)
                {
                    // Neste caso sabemos exatamente quantos bytes voltam
                    retorno = new byte[length];
                    inputstream.read(retorno);
                }
                else
                {
                    ByteArrayOutputStream bytestream =
                        new ByteArrayOutputStream();

                    int ch;

                    while ((ch = inputstream.read()) != -1)
                    {
                        bytestream.write(ch);
                    }

                    retorno = bytestream.toByteArray();

                    bytestream.close();
                }

            }
        }
        catch(IOException error)
        {
            return null;
        }
        finally
        {
            if (inputstream!= null)
            {
                try
                {
                    inputstream.close();
                }
                catch( Exception error)
                {
                  /*log error*/
                }
            }

            if (connection != null)
            {
                try
                {
                    connection.close();
                }
                catch( Exception error)
                {
                    /*log error*/
                }
            }
        }
        return retorno;
    }
}

/**
 * @author mhack
 */
public class CSocialMIDlet extends MIDlet implements CommandListener {

    private boolean midletPaused = false;
    usuario _usuario;
    mensagem[] _mensagens;
    mensagem _mensagem;
    String cookie;

    public void setCookie(String cookie)
    {
        this.cookie = cookie;
    }

    public String getCookie()
    {
        return cookie;
    }

    public void setUsuario(usuario u)
    {
        _usuario = u;
    }

    public void setMensagens(mensagem[] ms)
    {
        _mensagens = ms;
    }

    public void setMensagem(mensagem m)
    {
        _mensagem = m;
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command exitCommand;
    private Command backCmdExibirRecado;
    private Command backCmdMenuPrincipal;
    private Command backCmdListarRecados;
    private Command exitCmdLoginScreen;
    private Command exitCommand2;
    private Command voltarErroLogin;
    private Command okCmdLocalizarAmigos;
    private Command backCommand3;
    private Command okCommand1;
    private Command backCmdExibirAmigo;
    private Command backCmdLocalizarAmigos;
    private Command exitCmdMenuPrincipal;
    private Form formExibirRecado;
    private LoginScreen loginScreen;
    private List formListarRecados;
    private List listMenuPrincipal;
    private Alert alertFalhaLogin;
    private Form formExibirAmigo;
    private Form formLocalizarAmigos;
    private TextField tfNome;
    private DateField dfNasc;
    private TextField tfEmail;
    private List listAmigos;
    //</editor-fold>//GEN-END:|fields|0|

    /**
     * The CSocialMIDlet constructor.
     */
    public CSocialMIDlet() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        switchDisplayable(null, getLoginScreen());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
        if ( nextDisplayable == listMenuPrincipal )
            listMenuPrincipal.setTitle("Olá, " + _usuario.nickName);
        else if ( nextDisplayable == formListarRecados )
        {
            formListarRecados.deleteAll();
            for(int i = 0; i < _mensagens.length ; i++)
            {
                formListarRecados.append(_mensagens[i].subject, null);
            }
        }
        else if ( nextDisplayable == formExibirRecado )
        {
            StringItem assunto,
                    data,
                    remetente,
                    texto;

            assunto = (StringItem)formExibirRecado.get(0);
            data = (StringItem)formExibirRecado.get(1);
            remetente = (StringItem)formExibirRecado.get(2);
            texto = (StringItem)formExibirRecado.get(3);

            assunto.setText(_mensagem.subject);
            data.setText(_mensagem.postDate);
            remetente.setText("(" + _mensagem.owner.nickName + ")" + _mensagem.owner.realName);
            texto.setText(_mensagem.text);
        }
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        if (displayable == alertFalhaLogin) {//GEN-BEGIN:|7-commandAction|1|58-preAction
            if (command == voltarErroLogin) {//GEN-END:|7-commandAction|1|58-preAction
                switchDisplayable(null, getLoginScreen());//GEN-LINE:|7-commandAction|2|58-postAction
			}
		}
		else if (displayable == formExibirAmigo) {//GEN-BEGIN:|7-commandAction|1|71-preAction
            if (command == backCmdExibirAmigo) {//GEN-END:|7-commandAction|1|71-preAction
                // write pre-action user code here
                switchDisplayable(null, getListMenuPrincipal());//GEN-LINE:|7-commandAction|2|71-postAction
                // write post-action user code here
            }                                           
        } else if (displayable == formExibirRecado) {
            if (command == backCmdExibirRecado) {
                // write pre-action user code here
                switchDisplayable(null, getFormListarRecados());                                           
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|3|27-preAction
        } else if (displayable == formExibirRecado) {
            if (command == backCmdExibirRecado) {//GEN-END:|7-commandAction|3|27-preAction
                // write pre-action user code here
                switchDisplayable(null, getListMenuPrincipal());//GEN-LINE:|7-commandAction|4|27-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|48-preAction
        } else if (displayable == formListarRecados) {
            if (command == backCmdListarRecados) {//GEN-END:|7-commandAction|5|48-preAction
                switchDisplayable(null, getListMenuPrincipal());//GEN-LINE:|7-commandAction|6|48-postAction
                // write post-action user code here//GEN-LINE:|7-commandAction|8|
            }
            else if ( command == List.SELECT_COMMAND )
            {
                String __selectedString = formListarRecados.getString(formListarRecados.getSelectedIndex());
                setMensagem(null);

                if (__selectedString != null) {
                    for ( int i = 0 ; i < _mensagens.length ; i++ )
                    {
                        if ( __selectedString.equals(_mensagens[i].subject) )
                            setMensagem(_mensagens[i]);
                    }
                }

                if ( _mensagem != null )
                    switchDisplayable(null, getFormExibirRecado());
            }
        } else if (displayable == formLocalizarAmigos) {
            if (command == backCmdLocalizarAmigos) {                                         
                // write pre-action user code here
                switchDisplayable(null, getListMenuPrincipal());//GEN-LINE:|7-commandAction|8|65-postAction
                // write post-action user code here
            } else if (command == okCmdLocalizarAmigos) {//GEN-LINE:|7-commandAction|9|59-preAction
                // write pre-action user code here
//GEN-LINE:|7-commandAction|10|59-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|11|78-preAction
        } else if (displayable == listAmigos) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|11|78-preAction
                // write pre-action user code here
                listAmigosAction();//GEN-LINE:|7-commandAction|12|78-postAction
                // write post-action user code here
            }                                            
        } else if (displayable == listMenuPrincipal) {
                                    
            if (command == List.SELECT_COMMAND) {                                          
                // write pre-action user code here
                listMenuPrincipalAction();//GEN-LINE:|7-commandAction|14|36-postAction
                // write post-action user code here
            } else if (command == backCmdMenuPrincipal) {//GEN-LINE:|7-commandAction|15|43-preAction
                // write pre-action user code here
                switchDisplayable(null, getLoginScreen());//GEN-LINE:|7-commandAction|16|43-postAction
                // write post-action user code here
            } else if (command == exitCmdMenuPrincipal) {//GEN-LINE:|7-commandAction|17|75-preAction
                // write pre-action user code here
//GEN-LINE:|7-commandAction|18|75-postAction
                // write post-action user code here
            }                                            
        } else if (displayable == loginScreen) {
            if (command == LoginScreen.LOGIN_COMMAND) {                                          
                // write pre-action user code here

                // Limpa a sessão, se existir
                setCookie(null);

                ExecutaSolicitacao es = new ExecutaSolicitacao(this,
                        getListMenuPrincipal(),
                        getAlertFalhaLogin(),
                        "login",
                        ExecutaSolicitacao.USUARIO,
                        new String[] { "username", "password" },
                        new String[] { loginScreen.getUsername(), loginScreen.getPassword()});
                Thread t = new Thread(es);

                t.start();
//GEN-LINE:|7-commandAction|12|24-postAction
                // write post-action user code here
            } else if (command == exitCmdLoginScreen) {//GEN-LINE:|7-commandAction|21|52-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|22|52-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|15|7-postCommandAction
        }//GEN-END:|7-commandAction|15|7-postCommandAction
            }                                                   

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|18-getter|1|18-postInit
            // write post-init user code here
        }//GEN-BEGIN:|18-getter|2|
        return exitCommand;
    }
    //</editor-fold>//GEN-END:|18-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formExibirRecado ">//GEN-BEGIN:|14-getter|0|14-preInit
    /**
     * Returns an initiliazed instance of formExibirRecado component.
     * @return the initialized component instance
     */
    public Form getFormExibirRecado() {
        if (formExibirRecado == null) {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
            formExibirRecado = new Form("Recado", 
                new Item[] {
                new StringItem("Assunto:", null),
                new StringItem("Enviado em:", null),
                new StringItem("Enviado por:", null),
                new StringItem("Texto:", null)
            });
            formExibirRecado.addCommand(getBackCmdExibirRecado());
            formExibirRecado.setCommandListener(this);                                  
            // write post-init user code here
        }//GEN-BEGIN:|14-getter|2|
        return formExibirRecado;
    }
    //</editor-fold>//GEN-END:|14-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCmdExibirRecado ">//GEN-BEGIN:|26-getter|0|26-preInit
    /**
     * Returns an initiliazed instance of backCmdExibirRecado component.
     * @return the initialized component instance
     */
    public Command getBackCmdExibirRecado() {
        if (backCmdExibirRecado == null) {//GEN-END:|26-getter|0|26-preInit
            // write pre-init user code here
            backCmdExibirRecado = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|26-getter|1|26-postInit
            // write post-init user code here
        }//GEN-BEGIN:|26-getter|2|
        return backCmdExibirRecado;
    }
    //</editor-fold>//GEN-END:|26-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: loginScreen ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initiliazed instance of loginScreen component.
     * @return the initialized component instance
     */
    public LoginScreen getLoginScreen() {
        if (loginScreen == null) {//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here
            loginScreen = new LoginScreen(getDisplay());//GEN-BEGIN:|22-getter|1|22-postInit
            loginScreen.setLabelTexts("Nome de usu\u00E1rio", "Senha");
            loginScreen.setTitle("Bem vindo ao CSocial");
            loginScreen.addCommand(LoginScreen.LOGIN_COMMAND);
            loginScreen.addCommand(getExitCmdLoginScreen());
            loginScreen.setCommandListener(this);
            loginScreen.setBGColor(-3355444);
            loginScreen.setFGColor(0);
            loginScreen.setPassword("teste");
            loginScreen.setUsername("daniel");
            loginScreen.setUseLoginButton(false);
            loginScreen.setLoginButtonText("Logon");//GEN-END:|22-getter|1|22-postInit
            // write post-init user code here
        }//GEN-BEGIN:|22-getter|2|
        return loginScreen;
    }
    //</editor-fold>//GEN-END:|22-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formListarRecados ">//GEN-BEGIN:|25-getter|0|25-preInit
    /**
     * Returns an initiliazed instance of formListarRecados component.
     * @return the initialized component instance
     */
    public List getFormListarRecados() {
        if (formListarRecados == null) {//GEN-END:|25-getter|0|25-preInit
            // write pre-init user code here
            formListarRecados = new List("Recados", Choice.IMPLICIT);//GEN-BEGIN:|25-getter|1|25-postInit
            formListarRecados.addCommand(getBackCmdListarRecados());
            formListarRecados.setCommandListener(this);//GEN-END:|25-getter|1|25-postInit
            // write post-init user code here
        }//GEN-BEGIN:|25-getter|2|
        return formListarRecados;
    }
    //</editor-fold>//GEN-END:|25-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCmdMenuPrincipal ">//GEN-BEGIN:|42-getter|0|42-preInit
    /**
     * Returns an initiliazed instance of backCmdMenuPrincipal component.
     * @return the initialized component instance
     */
    public Command getBackCmdMenuPrincipal() {
        if (backCmdMenuPrincipal == null) {//GEN-END:|42-getter|0|42-preInit
            // write pre-init user code here
            backCmdMenuPrincipal = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|42-getter|1|42-postInit
            // write post-init user code here
        }//GEN-BEGIN:|42-getter|2|
        return backCmdMenuPrincipal;
    }
    //</editor-fold>//GEN-END:|42-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCmdListarRecados ">//GEN-BEGIN:|47-getter|0|47-preInit
    /**
     * Returns an initiliazed instance of backCmdListarRecados component.
     * @return the initialized component instance
     */
    public Command getBackCmdListarRecados() {
        if (backCmdListarRecados == null) {//GEN-END:|47-getter|0|47-preInit
            // write pre-init user code here
            backCmdListarRecados = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|47-getter|1|47-postInit
            // write post-init user code here
        }//GEN-BEGIN:|47-getter|2|
        return backCmdListarRecados;
    }
    //</editor-fold>//GEN-END:|47-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listMenuPrincipal ">//GEN-BEGIN:|34-getter|0|34-preInit
    /**
     * Returns an initiliazed instance of listMenuPrincipal component.
     * @return the initialized component instance
     */
    public List getListMenuPrincipal() {
        if (listMenuPrincipal == null) {//GEN-END:|34-getter|0|34-preInit
            // write pre-init user code here
            listMenuPrincipal = new List("", Choice.IMPLICIT);//GEN-BEGIN:|34-getter|1|34-postInit
            listMenuPrincipal.append("Ver recados", null);
            listMenuPrincipal.append("Localizar amigo", null);
            listMenuPrincipal.append("Ver v\u00EDdeos", null);
            listMenuPrincipal.append("Ver fotos", null);
            listMenuPrincipal.addCommand(getBackCmdMenuPrincipal());
            listMenuPrincipal.addCommand(getExitCmdMenuPrincipal());
            listMenuPrincipal.setCommandListener(this);
            listMenuPrincipal.setSelectedFlags(new boolean[] { false, false, false, false });//GEN-END:|34-getter|1|34-postInit
            // write post-init user code here
        }//GEN-BEGIN:|34-getter|2|
        return listMenuPrincipal;
    }
    //</editor-fold>//GEN-END:|34-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listMenuPrincipalAction ">//GEN-BEGIN:|34-action|0|34-preAction
    /**
     * Performs an action assigned to the selected list element in the listMenuPrincipal component.
     */
    public void listMenuPrincipalAction() {//GEN-END:|34-action|0|34-preAction
        // enter pre-action user code here
        String __selectedString = getListMenuPrincipal().getString(getListMenuPrincipal().getSelectedIndex());//GEN-BEGIN:|34-action|1|38-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("Ver recados")) {//GEN-END:|34-action|1|38-preAction
                // write pre-action user code here
                ExecutaSolicitacao es = new ExecutaSolicitacao(this,
                        getFormListarRecados(),
                        null,
                        "message",
                        ExecutaSolicitacao.LISTA_RECADOS,
                        null, null);
                Thread t = new Thread(es);
                t.start();
                // write post-action user code here
            } else if (__selectedString.equals("Localizar amigo")) {//GEN-LINE:|34-action|3|39-preAction
                // write pre-action user code here
                switchDisplayable(null, getFormLocalizarAmigos());//GEN-LINE:|34-action|4|39-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Ver v\u00EDdeos")) {//GEN-LINE:|34-action|5|40-preAction
                // write pre-action user code here
//GEN-LINE:|34-action|6|40-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Ver fotos")) {//GEN-LINE:|34-action|7|41-preAction
                // write pre-action user code here
//GEN-LINE:|34-action|8|41-postAction
                // write post-action user code here
            }//GEN-BEGIN:|34-action|9|34-postAction
        }//GEN-END:|34-action|9|34-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|34-action|10|
    //</editor-fold>//GEN-END:|34-action|10|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCmdLoginScreen ">//GEN-BEGIN:|51-getter|0|51-preInit
    /**
     * Returns an initiliazed instance of exitCmdLoginScreen component.
     * @return the initialized component instance
     */
    public Command getExitCmdLoginScreen() {
        if (exitCmdLoginScreen == null) {//GEN-END:|51-getter|0|51-preInit
            // write pre-init user code here
            exitCmdLoginScreen = new Command("Sair", Command.EXIT, 0);//GEN-LINE:|51-getter|1|51-postInit
            // write post-init user code here
        }//GEN-BEGIN:|51-getter|2|
        return exitCmdLoginScreen;
    }
    //</editor-fold>//GEN-END:|51-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand2 ">//GEN-BEGIN:|53-getter|0|53-preInit
    /**
     * Returns an initiliazed instance of exitCommand2 component.
     * @return the initialized component instance
     */
    public Command getExitCommand2() {
        if (exitCommand2 == null) {//GEN-END:|53-getter|0|53-preInit
            // write pre-init user code here
            exitCommand2 = new Command("Sair", Command.EXIT, 0);//GEN-LINE:|53-getter|1|53-postInit
            // write post-init user code here
        }//GEN-BEGIN:|53-getter|2|
        return exitCommand2;
    }
    //</editor-fold>//GEN-END:|53-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: voltarErroLogin ">                                   
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCmdLocalizarAmigos ">                                   
    /**
     * Returns an initiliazed instance of voltarErroLogin component.
     * @return the initialized component instance
     */
    public Command getVoltarErroLogin() {
        if (voltarErroLogin == null) {                                 
            // write pre-init user code here
            voltarErroLogin = new Command("Voltar", Command.BACK, 0);                                   
            // write post-init user code here
        }                         
        return voltarErroLogin;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alertFalhaLogin ">                                   
    /**
     * Returns an initiliazed instance of alertFalhaLogin component.
     * @return the initialized component instance
     */
    public Alert getAlertFalhaLogin() {
        if (alertFalhaLogin == null) {                                 
            // write pre-init user code here
            alertFalhaLogin = new Alert("Falha no login", "Usu\u00E1rio ou senha inv\u00E1lidos!", null, AlertType.ERROR);                                    
            alertFalhaLogin.addCommand(getVoltarErroLogin());
            alertFalhaLogin.setCommandListener(this);
            alertFalhaLogin.setTimeout(Alert.FOREVER);                                  
            // write post-init user code here
        }                         
        return alertFalhaLogin;
    }
    //</editor-fold>                       

    /**
     * Returns an initiliazed instance of okCmdLocalizarAmigos component.
     * @return the initialized component instance
     */
    public Command getOkCmdLocalizarAmigos() {
        if (okCmdLocalizarAmigos == null) {                                 
            // write pre-init user code here
            okCmdLocalizarAmigos = new Command("Ok", Command.OK, 0);//GEN-LINE:|58-getter|1|58-postInit
            // write post-init user code here
        }//GEN-BEGIN:|58-getter|2|
        return okCmdLocalizarAmigos;
    }
    //</editor-fold>//GEN-END:|58-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand1 ">//GEN-BEGIN:|60-getter|0|60-preInit
    /**
     * Returns an initiliazed instance of okCommand1 component.
     * @return the initialized component instance
     */
    public Command getOkCommand1() {
        if (okCommand1 == null) {//GEN-END:|60-getter|0|60-preInit
            // write pre-init user code here
            okCommand1 = new Command("Ok", Command.OK, 0);//GEN-LINE:|60-getter|1|60-postInit
            // write post-init user code here
        }//GEN-BEGIN:|60-getter|2|
        return okCommand1;
    }
    //</editor-fold>//GEN-END:|60-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand3 ">//GEN-BEGIN:|62-getter|0|62-preInit
    /**
     * Returns an initiliazed instance of backCommand3 component.
     * @return the initialized component instance
     */
    public Command getBackCommand3() {
        if (backCommand3 == null) {//GEN-END:|62-getter|0|62-preInit
            // write pre-init user code here
            backCommand3 = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|62-getter|1|62-postInit
            // write post-init user code here
        }//GEN-BEGIN:|62-getter|2|
        return backCommand3;
    }
    //</editor-fold>//GEN-END:|62-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCmdLocalizarAmigos ">//GEN-BEGIN:|64-getter|0|64-preInit
    /**
     * Returns an initiliazed instance of backCmdLocalizarAmigos component.
     * @return the initialized component instance
     */
    public Command getBackCmdLocalizarAmigos() {
        if (backCmdLocalizarAmigos == null) {//GEN-END:|64-getter|0|64-preInit
            // write pre-init user code here
            backCmdLocalizarAmigos = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|64-getter|1|64-postInit
            // write post-init user code here
        }//GEN-BEGIN:|64-getter|2|
        return backCmdLocalizarAmigos;
    }
    //</editor-fold>//GEN-END:|64-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formLocalizarAmigos ">//GEN-BEGIN:|56-getter|0|56-preInit
    /**
     * Returns an initiliazed instance of formLocalizarAmigos component.
     * @return the initialized component instance
     */
    public Form getFormLocalizarAmigos() {
        if (formLocalizarAmigos == null) {//GEN-END:|56-getter|0|56-preInit
            // write pre-init user code here
            formLocalizarAmigos = new Form("Localizar Amigos", new Item[] { getTfNome(), getTfEmail(), getDfNasc() });//GEN-BEGIN:|56-getter|1|56-postInit
            formLocalizarAmigos.addCommand(getOkCmdLocalizarAmigos());
            formLocalizarAmigos.addCommand(getBackCmdLocalizarAmigos());
            formLocalizarAmigos.setCommandListener(this);//GEN-END:|56-getter|1|56-postInit
            // write post-init user code here
        }//GEN-BEGIN:|56-getter|2|
        return formLocalizarAmigos;
    }
    //</editor-fold>//GEN-END:|56-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formExibirAmigo ">//GEN-BEGIN:|57-getter|0|57-preInit
    /**
     * Returns an initiliazed instance of formExibirAmigo component.
     * @return the initialized component instance
     */
    public Form getFormExibirAmigo() {
        if (formExibirAmigo == null) {//GEN-END:|57-getter|0|57-preInit
            // write pre-init user code here
            formExibirAmigo = new Form("Exibir Amigo");//GEN-BEGIN:|57-getter|1|57-postInit
            formExibirAmigo.addCommand(getBackCmdExibirAmigo());
            formExibirAmigo.setCommandListener(this);//GEN-END:|57-getter|1|57-postInit
            // write post-init user code here
        }//GEN-BEGIN:|57-getter|2|
        return formExibirAmigo;
    }
    //</editor-fold>//GEN-END:|57-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCmdExibirAmigo ">//GEN-BEGIN:|70-getter|0|70-preInit
    /**
     * Returns an initiliazed instance of backCmdExibirAmigo component.
     * @return the initialized component instance
     */
    public Command getBackCmdExibirAmigo() {
        if (backCmdExibirAmigo == null) {//GEN-END:|70-getter|0|70-preInit
            // write pre-init user code here
            backCmdExibirAmigo = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|70-getter|1|70-postInit
            // write post-init user code here
        }//GEN-BEGIN:|70-getter|2|
        return backCmdExibirAmigo;
    }
    //</editor-fold>//GEN-END:|70-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCmdMenuPrincipal ">//GEN-BEGIN:|74-getter|0|74-preInit
    /**
     * Returns an initiliazed instance of exitCmdMenuPrincipal component.
     * @return the initialized component instance
     */
    public Command getExitCmdMenuPrincipal() {
        if (exitCmdMenuPrincipal == null) {//GEN-END:|74-getter|0|74-preInit
            // write pre-init user code here
            exitCmdMenuPrincipal = new Command("Sair", Command.EXIT, 0);//GEN-LINE:|74-getter|1|74-postInit
            // write post-init user code here
        }//GEN-BEGIN:|74-getter|2|
        return exitCmdMenuPrincipal;
    }
    //</editor-fold>//GEN-END:|74-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listAmigos ">//GEN-BEGIN:|77-getter|0|77-preInit
    /**
     * Returns an initiliazed instance of listAmigos component.
     * @return the initialized component instance
     */
    public List getListAmigos() {
        if (listAmigos == null) {//GEN-END:|77-getter|0|77-preInit
            // write pre-init user code here
            listAmigos = new List("Lista Amigos", Choice.IMPLICIT);//GEN-BEGIN:|77-getter|1|77-postInit
            listAmigos.setCommandListener(this);//GEN-END:|77-getter|1|77-postInit
            // write post-init user code here
        }//GEN-BEGIN:|77-getter|2|
        return listAmigos;
    }
    //</editor-fold>//GEN-END:|77-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listAmigosAction ">//GEN-BEGIN:|77-action|0|77-preAction
    /**
     * Performs an action assigned to the selected list element in the listAmigos component.
     */
    public void listAmigosAction() {//GEN-END:|77-action|0|77-preAction
        // enter pre-action user code here


        String __selectedString = getListAmigos().getString(getListAmigos().getSelectedIndex());//GEN-LINE:|77-action|1|77-postAction
        // enter post-action user code here


    }//GEN-BEGIN:|77-action|2|
    //</editor-fold>//GEN-END:|77-action|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfNome ">//GEN-BEGIN:|81-getter|0|81-preInit
    /**
     * Returns an initiliazed instance of tfNome component.
     * @return the initialized component instance
     */
    public TextField getTfNome() {
        if (tfNome == null) {//GEN-END:|81-getter|0|81-preInit
            // write pre-init user code here
            tfNome = new TextField("Nome", null, 32, TextField.ANY);//GEN-LINE:|81-getter|1|81-postInit
            // write post-init user code here
        }//GEN-BEGIN:|81-getter|2|
        return tfNome;
    }
    //</editor-fold>//GEN-END:|81-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: dfNasc ">//GEN-BEGIN:|82-getter|0|82-preInit
    /**
     * Returns an initiliazed instance of dfNasc component.
     * @return the initialized component instance
     */
    public DateField getDfNasc() {
        if (dfNasc == null) {//GEN-END:|82-getter|0|82-preInit
            // write pre-init user code here
            dfNasc = new DateField("Data de Nascimento", DateField.DATE);//GEN-BEGIN:|82-getter|1|82-postInit
            dfNasc.setDate(new java.util.Date(System.currentTimeMillis()));//GEN-END:|82-getter|1|82-postInit
            // write post-init user code here
        }//GEN-BEGIN:|82-getter|2|
        return dfNasc;
    }
    //</editor-fold>//GEN-END:|82-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfEmail ">//GEN-BEGIN:|83-getter|0|83-preInit
    /**
     * Returns an initiliazed instance of tfEmail component.
     * @return the initialized component instance
     */
    public TextField getTfEmail() {
        if (tfEmail == null) {//GEN-END:|83-getter|0|83-preInit
            // write pre-init user code here
            tfEmail = new TextField("E-mail", null, 32, TextField.ANY);//GEN-LINE:|83-getter|1|83-postInit
            // write post-init user code here
        }//GEN-BEGIN:|83-getter|2|
        return tfEmail;
    }
    //</editor-fold>//GEN-END:|83-getter|2|



    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }


    
}
