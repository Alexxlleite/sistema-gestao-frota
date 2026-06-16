package DataAccess;

import DomainModel.Veiculo;
import java.util.List;
import javax.persistence.EntityManager;

public class VeiculoDAO extends GenericDAO<Veiculo> {

    private static VeiculoDAO instance;

    private VeiculoDAO() {
        super(Veiculo.class);
    }

    public static VeiculoDAO getInstance() {
        if (instance == null) {
            instance = new VeiculoDAO();
        }
        return instance;
    }

    public List<Veiculo> buscarPorPlaca(String placa) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery(
                    "SELECT v FROM Veiculo v WHERE LOWER(v.placa) LIKE :placa",
                    Veiculo.class)
                    .setParameter("placa", "%" + placa.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Veiculo> buscarPorStatus(String status) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery(
                    "SELECT v FROM Veiculo v WHERE LOWER(v.status) LIKE :status",
                    Veiculo.class)
                    .setParameter("status", "%" + status.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}