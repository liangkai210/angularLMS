package com.kailiang.gcitlms.dao;

import com.kailiang.gcitlms.bean.Borrower;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowerDao extends BaseDao<Borrower> implements ResultSetExtractor<List<Borrower>> {

    public void addBorrower(Borrower borrower) {
        String sql = "INSERT INTO tbl_borrower(name, address, phone, username, password) VALUES (?, ?, ?, ?, ?)";
        template.update(sql, new Object[]{borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getUsername(), borrower.getPassword()});
    }

    public void updateBorrower(Borrower borrower) {
        String sql = "UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?";
        template.update(sql, new Object[]{borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo()});
    }

    public void deleteBorrower(Borrower borrower) {
        String sql = "DELETE FROM tbl_borrower WHERE cardNo = ?";
        template.update(sql, borrower.getCardNo());
    }

    public Borrower getBorrowerWithId(Borrower borrower) {
        String sql = "SELECT * FROM tbl_borrower WHERE cardNo = ?";
        List<Borrower> borrowerTemp = template.query(sql, new Object[]{borrower.getCardNo()}, this);
        if (borrowerTemp != null) {
            return borrowerTemp.get(0);
        }
        return null;
    }

    public boolean validateBorrower(String username) {
        String sql = "SELECT * FROM tbl_borrower WHERE username = ?";
        List<Borrower> borrowers = template.query(sql, new Object[]{username}, this);
        return borrowers != null && !borrowers.isEmpty();
    }

    public Integer loginBorrower(Borrower borrower) {
        String sql = "SELECT * FROM tbl_borrower WHERE username = ? AND password = ?";
        List<Borrower> borrowers = template.query(sql, new Object[]{borrower.getUsername(), borrower.getPassword()}, this);
        if (borrowers != null && !borrowers.isEmpty()) {
            return borrowers.get(0).getCardNo();
        }
        return -1;
    }

    public List<Borrower> getAllBorrowers(String searchString) {
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_borrower where name like ?";
            return template.query(sql, new Object[]{searchString}, this);
        } else {
            String sql = "select * from tbl_borrower";
            return template.query(sql, this);
        }
    }

    public int getCount() {
        String sql = "SELECT COUNT(*) FROM tbl_borrower";
        return template.queryForObject(sql, Integer.class);
    }

    public int getCountLike(String branchName) {
        String sql = "SELECT count(*) FROM tbl_borrower WHERE name like ?";
        return template.queryForObject(sql, new Object[]{"%" + branchName + "%"}, Integer.class);
    }

    @Override
    public List<Borrower> extractData(ResultSet rs) {
        List<Borrower> borrowers = new ArrayList<Borrower>();
        try {
            while (rs.next()) {
                Borrower bor = new Borrower();
                bor.setCardNo(rs.getInt("cardNo"));
                bor.setName(rs.getString("name"));
                bor.setAddress(rs.getString("address"));
                bor.setPhone(rs.getString("phone"));
                bor.setUsername(rs.getString("username"));
                bor.setPassword(rs.getString("password"));
                borrowers.add(bor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowers;
    }
}
