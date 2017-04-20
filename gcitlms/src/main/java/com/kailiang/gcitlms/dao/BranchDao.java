package com.kailiang.gcitlms.dao;

import com.kailiang.gcitlms.bean.Branch;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchDao extends BaseDao<Branch> implements ResultSetExtractor<List<Branch>> {

    public void addBranch(Branch branch) {
        String sql = "INSERT INTO tbl_library_branch(branchName, branchAddress) VALUES (?, ?)";
        template.update(sql, branch.getBranchName(), branch.getBranchAddress());
    }

    public void updateBranch(Branch branch) {
        String sql = "UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?";
        template.update(sql, branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId());
    }

    public void deleteBranch(Branch branch) {
        String sql = "DELETE FROM tbl_library_branch WHERE branchId = ?";
        template.update(sql, branch.getBranchId());
    }

    public Branch getBranchWithId(Branch branch) {
        String sql = "SELECT * FROM tbl_library_branch WHERE branchId = ?";
        List<Branch> branches = template.query(sql, new Object[]{branch.getBranchId()}, this);
        if (branches != null) {
            return branches.get(0);
        }
        return null;
    }

    public List<Branch> getAllBranches(String searchString) {
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_library_branch where branchName like ?";
            return template.query(sql, new Object[]{searchString}, this);
        } else {
            String sql = "select * from tbl_library_branch";
            return template.query(sql, this);
        }
    }

    public int getCount() {
        String sql = "SELECT COUNT(*) FROM tbl_library_branch";
        return template.queryForObject(sql, Integer.class);
    }

    public int getCountLike(String branchName) {
        String sql = "SELECT count(*) FROM tbl_library_branch WHERE branchName like ?";
        return template.queryForObject(sql, new Object[]{"%" + branchName + "%"}, Integer.class);
    }

    @Override
    public List<Branch> extractData(ResultSet rs) {
        List<Branch> branches = new ArrayList<Branch>();
        try {
            while (rs.next()) {
                Branch br = new Branch();
                br.setBranchId(rs.getInt("branchId"));
                br.setBranchName(rs.getString("branchName"));
                br.setBranchAddress(rs.getString("branchAddress"));
                branches.add(br);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branches;
    }
}
