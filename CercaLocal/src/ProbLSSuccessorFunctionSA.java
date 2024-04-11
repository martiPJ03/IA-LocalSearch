
//~--- non-JDK imports --------------------------------------------------------


import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ProbLSSuccessorFunctionSA implements SuccessorFunction {

    @SuppressWarnings("unchecked")
    public List getSuccessors(Object aState) { // retorna ArrayList on cada element es un sucessor
        ArrayList retVal = new ArrayList();
        ProbLSBoard pare = (ProbLSBoard) aState;
        ProbLSHeuristicFunction LSHF  = new ProbLSHeuristicFunction();
        Random myRandom=new Random();

        ArrayList<Integer> users = pare.getUsersId();

        int num_user, pos_fitxer, num_server;

        num_user = myRandom.nextInt(users.size());

        int id_user = users.get(num_user);

        pos_fitxer = myRandom.nextInt(pare.getActualBoard().get(id_user).size());

        int num_fitxer = pare.getActualBoard().get(id_user).get(pos_fitxer).getFirst();

        do{
            num_server = myRandom.nextInt(pare.getNumServers());
        } while( !(pare.validFileServer(num_fitxer, num_server) && num_server != pare.getActualBoard().get(id_user).get(pos_fitxer).getSecond()) ); // mentres el server no sigui possible buscar un nou


        ProbLSBoard fill = new ProbLSBoard(pare.getNumServers(), pare.getNumUsers(), pare.getFileLoc(), pare.getTransTime(), pare.getActualBoard());

        fill.changeTransmittingServer(id_user, num_fitxer, num_server);

        double valor = LSHF.getHeuristicValue(fill);
        String string_info = "Fitxer " + num_fitxer + " del usuari " + id_user + " passa del servidor " + pare.getActualBoard().get(id_user).get(pos_fitxer).getSecond() + " al servidor " + num_server + " cost(" + valor +")";
        System.out.println(string_info);
        // afegeix un fill al valor de return
        retVal.add(new Successor(string_info, fill));

        return retVal;

    }

}
