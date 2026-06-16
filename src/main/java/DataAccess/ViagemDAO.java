package DataAccess;

import DomainModel.Viagem;

public class ViagemDAO extends GenericDAO<Viagem> {
    private static ViagemDAO instance;

    private ViagemDAO() {
        super(Viagem.class);
    }

    public static ViagemDAO getInstance() {
        if (instance == null) {
            instance = new ViagemDAO();
        }
        return instance;
    }
}
