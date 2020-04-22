package com.hs.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0 ���ӳع�����
 *
 * @author weidong
 */
public class C3P0Utils {
    private static ComboPooledDataSource ds = new ComboPooledDataSource("jf");
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    /**
     * ��ȡ����Դ
     *
     * @return ���ӳ�
     */
    public static DataSource getDataSource() {
        return ds;
    }

    /**
     * �ӵ�ǰ�߳��ϻ�ȡ����
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = tl.get();
        if (conn == null) {
            conn = ds.getConnection();
            // �͵�ǰ�̰߳�
            tl.set(conn);
        }
        return conn;
    }

    /**
     * �ͷ���Դ
     */
    public static void closeResource(Connection conn, Statement st, ResultSet rs) {
        closeResource(st, rs);
        closeConn(conn);
    }

    public static void closeResource(Statement st, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
    }

    /**
     * �ͷ�����
     */
    public static void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                // �͵�ǰ���߳̽��
                tl.remove();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }

    }

    /**
     * �ͷ����ִ����
     *
     * @param st ���ִ����
     */
    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            st = null;
        }

    }

    /**
     * �ͷŽ����
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }

    }

    /**
     * ��������
     */
    public static void startTransaction() throws SQLException {
        // ��ȡ����//��������
        getConnection().setAutoCommit(false);
        ;
    }

    /**
     * �����ύ
     */
    public static void commitAndClose() {
        try {
            // ��ȡ����
            Connection conn = getConnection();
            // �ύ����
            conn.commit();
            // �ͷ���Դ
            conn.close();
            // �����
            tl.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����ع�
     */
    public static void rollbackAndClose() {
        try {
            // ��ȡ����
            Connection conn = getConnection();
            // ����ع�
            conn.rollback();
            // �ͷ���Դ
            conn.close();
            // �����
            tl.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
