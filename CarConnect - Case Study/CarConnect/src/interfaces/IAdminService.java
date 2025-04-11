package interfaces;

import entity.Admin;

public interface IAdminService {
    Admin getAdminById(int adminId);
    Admin getAdminByUsername(String username);
    boolean registerAdmin(Admin admin);
    boolean updateAdmin(Admin admin);
    boolean deleteAdmin(int adminId);
}
