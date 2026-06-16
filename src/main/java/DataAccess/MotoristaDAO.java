package DataAccess;

import DomainModel.Motorista;

public class MotoristaDAO extends GenericDAO<Motorista> {
    private static MotoristaDAO instance;

    private MotoristaDAO() {
        super(Motorista.class);
    }

    public static MotoristaDAO getInstance() {
        if (instance == null) {
            instance = new MotoristaDAO();
        }
        return instance;
    }
}
