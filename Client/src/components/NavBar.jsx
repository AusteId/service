import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import logo from "../assets/logo.svg";
import styles from "./NavBar.module.css";
import { FaSignOutAlt } from "react-icons/fa";
import { LuCrown } from "react-icons/lu";
import { FiUser } from "react-icons/fi";

const NavBar = () => {
  const { user, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav className={styles.nav}>
      <div className={styles.logoContainer}>
        <img src={logo} alt="logo" className="pt-1"></img>
        <span className={styles.appName}>The Final Bug</span>
      </div>

      <ul className={styles.navList}>
        {isAuthenticated ? (
          <>
            <li className={styles.navItem}>
              <NavLink to="/bigCards">Car services</NavLink>
            </li>
            <li className={styles.navItem}>
              <NavLink to="/employees">Employees</NavLink>
            </li>
            {user?.roles?.includes("ROLE_ADMIN") && (
              <li className={styles.navItem}>
                <NavLink to="/admin">Admin Panel</NavLink>
              </li>
            )}
          </>
        ) : (
          <>
            <li className={styles.navItem}>
              <NavLink to="/login">Login</NavLink>
            </li>
            <li className={styles.navItem}>
              <NavLink to="/register">Sign Up</NavLink>
            </li>
          </>
        )}
      </ul>

      {isAuthenticated && (
        <div className={styles.userSection}>
          <span className={styles.welcomeText}>
            {user?.roles?.includes("ADMIN") ? (
              <LuCrown style={{ color: "#00c46a" }} />
            ) : (
              <FiUser style={{ color: "#aaa" }} />
            )}
            Welcome, {user.email}</span>
          <button onClick={handleLogout} className={styles.logoutButton}>
            <FaSignOutAlt /> Logout
          </button>
        </div>
      )}

    </nav>
  );
};

export default NavBar;
