package DataAccess;

import DomainModel.Manutencao;

public class ManutencaoDAO extends GenericDAO<Manutencao> {
    private static ManutencaoDAO instance;

    private ManutencaoDAO() {
        super(Manutencao.class);
    }

    public static ManutencaoDAO getInstance() {
        if (instance == null) {
            instance = new ManutencaoDAO();
        }
        return instance;
    }
}
