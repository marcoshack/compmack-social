/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compmack.social.client.mobile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import org.netbeans.microedition.lcdui.LoginScreen;
//import org.kxml.*;
//import org.kxml.kdom.*;
//import org.kxml.parser.*;

/**
 * @author mhack
 */
public class CSocialMIDlet extends MIDlet implements CommandListener {

    private boolean midletPaused = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command exitCommand;
    private Command backCommand;
    private Command backCommand1;
    private Command backCommand2;
    private Command exitCommand1;
    private Command exitCommand2;
    private Form formExibirRecado;
    private LoginScreen loginScreen;
    private Form formListarRecados;
    private List listMenuPrincipal;
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
        if (displayable == formExibirRecado) {//GEN-BEGIN:|7-commandAction|1|27-preAction
            if (command == backCommand) {//GEN-END:|7-commandAction|1|27-preAction
                // write pre-action user code here
                switchDisplayable(null, getFormListarRecados());//GEN-LINE:|7-commandAction|2|27-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|3|48-preAction
        } else if (displayable == formListarRecados) {
            if (command == backCommand2) {//GEN-END:|7-commandAction|3|48-preAction
                // write pre-action user code here
                switchDisplayable(null, getListMenuPrincipal());//GEN-LINE:|7-commandAction|4|48-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|36-preAction
        } else if (displayable == listMenuPrincipal) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|5|36-preAction
                // write pre-action user code here
                listMenuPrincipalAction();//GEN-LINE:|7-commandAction|6|36-postAction
                // write post-action user code here
            } else if (command == backCommand1) {//GEN-LINE:|7-commandAction|7|43-preAction
                // write pre-action user code here
                switchDisplayable(null, getLoginScreen());//GEN-LINE:|7-commandAction|8|43-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|9|24-preAction
        } else if (displayable == loginScreen) {
            if (command == LoginScreen.LOGIN_COMMAND) {//GEN-END:|7-commandAction|9|24-preAction
                // write pre-action user code here
                switchDisplayable(null, getListMenuPrincipal());//GEN-LINE:|7-commandAction|10|24-postAction
                // write post-action user code here
            } else if (command == exitCommand1) {//GEN-LINE:|7-commandAction|11|52-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|12|52-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|13|7-postCommandAction
        }//GEN-END:|7-commandAction|13|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|14|
    //</editor-fold>//GEN-END:|7-commandAction|14|

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
            formExibirRecado = new Form("Recado", new Item[] { });//GEN-BEGIN:|14-getter|1|14-postInit
            formExibirRecado.addCommand(getBackCommand());
            formExibirRecado.setCommandListener(this);//GEN-END:|14-getter|1|14-postInit
            // write post-init user code here
        }//GEN-BEGIN:|14-getter|2|
        return formExibirRecado;
    }
    //</editor-fold>//GEN-END:|14-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|26-getter|0|26-preInit
    /**
     * Returns an initiliazed instance of backCommand component.
     * @return the initialized component instance
     */
    public Command getBackCommand() {
        if (backCommand == null) {//GEN-END:|26-getter|0|26-preInit
            // write pre-init user code here
            backCommand = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|26-getter|1|26-postInit
            // write post-init user code here
        }//GEN-BEGIN:|26-getter|2|
        return backCommand;
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
            loginScreen.addCommand(getExitCommand1());
            loginScreen.setCommandListener(this);
            loginScreen.setBGColor(-3355444);
            loginScreen.setFGColor(0);
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
    public Form getFormListarRecados() {
        if (formListarRecados == null) {//GEN-END:|25-getter|0|25-preInit
            // write pre-init user code here
            formListarRecados = new Form("Recados");//GEN-BEGIN:|25-getter|1|25-postInit
            formListarRecados.addCommand(getBackCommand2());
            formListarRecados.setCommandListener(this);//GEN-END:|25-getter|1|25-postInit
            // write post-init user code here
        }//GEN-BEGIN:|25-getter|2|
        return formListarRecados;
    }
    //</editor-fold>//GEN-END:|25-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand1 ">//GEN-BEGIN:|42-getter|0|42-preInit
    /**
     * Returns an initiliazed instance of backCommand1 component.
     * @return the initialized component instance
     */
    public Command getBackCommand1() {
        if (backCommand1 == null) {//GEN-END:|42-getter|0|42-preInit
            // write pre-init user code here
            backCommand1 = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|42-getter|1|42-postInit
            // write post-init user code here
        }//GEN-BEGIN:|42-getter|2|
        return backCommand1;
    }
    //</editor-fold>//GEN-END:|42-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand2 ">//GEN-BEGIN:|47-getter|0|47-preInit
    /**
     * Returns an initiliazed instance of backCommand2 component.
     * @return the initialized component instance
     */
    public Command getBackCommand2() {
        if (backCommand2 == null) {//GEN-END:|47-getter|0|47-preInit
            // write pre-init user code here
            backCommand2 = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|47-getter|1|47-postInit
            // write post-init user code here
        }//GEN-BEGIN:|47-getter|2|
        return backCommand2;
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
            listMenuPrincipal = new List("Menu Principal", Choice.IMPLICIT);//GEN-BEGIN:|34-getter|1|34-postInit
            listMenuPrincipal.append("Ver recados", null);
            listMenuPrincipal.append("Localizar amigo", null);
            listMenuPrincipal.append("Ver v\u00EDdeos", null);
            listMenuPrincipal.append("Ver fotos", null);
            listMenuPrincipal.addCommand(getBackCommand1());
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
                switchDisplayable(null, getFormListarRecados());//GEN-LINE:|34-action|2|38-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Localizar amigo")) {//GEN-LINE:|34-action|3|39-preAction
                // write pre-action user code here
//GEN-LINE:|34-action|4|39-postAction
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

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand1 ">//GEN-BEGIN:|51-getter|0|51-preInit
    /**
     * Returns an initiliazed instance of exitCommand1 component.
     * @return the initialized component instance
     */
    public Command getExitCommand1() {
        if (exitCommand1 == null) {//GEN-END:|51-getter|0|51-preInit
            // write pre-init user code here
            exitCommand1 = new Command("Sair", Command.EXIT, 0);//GEN-LINE:|51-getter|1|51-postInit
            // write post-init user code here
        }//GEN-BEGIN:|51-getter|2|
        return exitCommand1;
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

    private void executaChamada()
    {
        //XmlParser parser;
    }

    /**
     * Executa a chamada http e devolve o array de bytes com o conteúdo
     * @param url Url a recuperar
     * @param parametros Parâmetros a serem enviados pela solicitação
     * @return dados recebidos, ou null se houve algum erro
     */
    private byte[] recuperaConteudo(String url, String[] parametros)
    {
      HttpConnection connection = null;
      InputStream inputstream = null;
      byte[] retorno = null;

      try
      {
        connection = (HttpConnection) Connector.open(url);
        //HTTP Request
        connection.setRequestMethod(HttpConnection.GET);
        //connection.setRequestProperty("Content-Type","//text plain");
        connection.setRequestProperty("Connection", "close");
        // HTTP Response
        //System.out.println("Status Line Code: " + connection.getResponseCode());
        //System.out.println("Status Line Message: " + connection.getResponseMessage());

        if (connection.getResponseCode() == HttpConnection.HTTP_OK)
        {
          //System.out.println(
          //  connection.getHeaderField(0)+ " " + connection.getHeaderFieldKey(0));
          //System.out.println(
          // "Header Field Date: " + connection.getHeaderField("date"));
          //String str;
          inputstream = connection.openInputStream();
          int length = (int) connection.getLength();

          if (length != -1)
          {
              // Neste caso sabemos exatamente quantos bytes voltam
            //byte incomingData[] = new byte[length];
              retorno = new byte[length];
            inputstream.read(retorno);
            //str = new String(incomingData);
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
            //str = new String(bytestream.toByteArray());
            bytestream.close();
          }
          //System.out.println(str);
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
