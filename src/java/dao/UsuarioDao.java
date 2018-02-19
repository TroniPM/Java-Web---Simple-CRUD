/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;

/**
 *
 * @author Mateus
 */
public class UsuarioDao {

    private static final String SQL_INSERT = "INSERT INTO usuario (nome, email) VALUES (?,?)";
    private static final String SQL_UPDATE = "UPDATE usuario SET nome = ?, email = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM usuario";
    private static final String SQL_FIND_BY_NOME = "SELECT * FROM usuario WHERE nome like ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM usuario WHERE id = ?";

    public String erro = "";

    public boolean insert(Usuario entity) {
        boolean ret = false;
        Connection connection = Conexao.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT);
            ps.setString(1, entity.getNome());
            ps.setString(2, entity.getEmail());

            ps.executeUpdate();

            Logger.getLogger(Conexao.class.getName()).log(Level.INFO, "Usuário inserido com sucesso");
            ret = true;
        } catch (SQLException e) {
            Logger.getLogger(Conexao.class.getName()).log(Level.INFO, null, e);
            erro = e.getMessage();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                erro += "<br>\n" + ex.getMessage();
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    public List<Usuario> getAll() {
        List<Usuario> usuarios = new ArrayList<Usuario>();

        ResultSet rs = null;
        Connection connection = Conexao.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_FIND_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                usuarios.add(getUsuario(rs));
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return usuarios;
    }

    public List<Usuario> findByName(String nome) {

        List<Usuario> fornecedores = new ArrayList<Usuario>();

        ResultSet rs = null;
        Connection connection = Conexao.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_FIND_BY_NOME);
            ps.setString(1, "%" + nome);

            rs = ps.executeQuery();
            rs.next();

            while (rs.next()) {
                fornecedores.add(getUsuario(rs));
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return fornecedores;
    }

    public Usuario findById(Long id) {
        Connection connection = Conexao.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            ps = connection.prepareStatement(SQL_FIND_BY_ID);
            ps.setLong(1, id);

            rs = ps.executeQuery();
            rs.next();

            usuario = getUsuario(rs);

            Logger.getLogger(Conexao.class.getName()).log(Level.INFO, "Usuário recuperado com sucesso!\n ".concat(usuario.toString()));

        } catch (SQLException e) {
            Logger.getLogger(Conexao.class.getName()).log(Level.INFO, "Erro ao recuperar Usuário!\n ", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return usuario;
    }

    private Usuario getUsuario(ResultSet rs) throws SQLException {
        Usuario fornecedor = new Usuario(rs.getLong("id"), rs.getString("nome"), rs.getString("email"));

        return fornecedor;
    }

    public Usuario update(Usuario entity) {

        Connection connection = Conexao.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(SQL_UPDATE);
            ps.setString(1, entity.getNome());
            ps.setString(2, entity.getEmail());
            ps.setLong(3, entity.getId());

            int qtdLinhas = ps.executeUpdate();

            if (0 <= qtdLinhas) {
                // logger.info("Nenhum registro alterado.");
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.INFO, "Nenhum registro alterado.");
            }

            Logger.getLogger(UsuarioDao.class.getName()).log(Level.INFO, "Usuário atualizado com sucesso!\n ".concat(entity.toString()));
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return entity;
    }

    public boolean delete(Long id) {

        Connection connection = Conexao.getConnection();
        PreparedStatement ps = null;
        boolean ret = false;
        try {
            ps = connection.prepareStatement(SQL_DELETE);
            ps.setLong(1, id);

            int qtdLinhas = ps.executeUpdate();

            if (0 <= qtdLinhas) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.INFO, "Nenhum registro apagado.");
            } else {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.INFO, "Usuário apagado com sucesso!");
            }

            ret = true;
        } catch (SQLException e) {
            erro = e.getMessage();
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.INFO, "Erro ao apagar Usuário", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                erro += "<br>\n" + ex.getMessage();
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

}
