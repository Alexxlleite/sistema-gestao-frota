package DataAccess;

import DomainModel.Usuario;
import java.util.List;
import javax.persistence.EntityManager;

public class UsuarioDAO extends GenericDAO<Usuario> {

    private static UsuarioDAO instance;

    private UsuarioDAO() {
        super(Usuario.class);
    }

    public static UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    public Usuario autenticar(String login, String senha) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            String senhaCriptografada = Criptografia.sha256(senha);

            List<Usuario> usuarios = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha AND u.status = :status",
                    Usuario.class)
                    .setParameter("login", login)
                    .setParameter("senha", senhaCriptografada)
                    .setParameter("status", "Ativo")
                    .getResultList();

            if (usuarios.isEmpty()) {
                return null;
            }

            return usuarios.get(0);

        } finally {
            em.close();
        }
    }

    public long contarUsuarios() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery("SELECT COUNT(u) FROM Usuario u", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public void criarAdminPadraoSeNaoExistir() {
        if (contarUsuarios() == 0) {
            Usuario admin = new Usuario();

            admin.setNome("Administrador");
            admin.setLogin("admin");
            admin.setSenha(Criptografia.sha256("admin"));
            admin.setPerfil("Administrador");
            admin.setStatus("Ativo");

            persist(admin);
        }
    }
}