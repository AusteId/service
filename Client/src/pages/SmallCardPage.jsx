import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import { useAuth } from "../context/AuthContext";
import Button from "../components/Button";
import SmallCard from "../components/SmallCard";
import styles from "./SmallCardPage.module.css";

const SmallCardPage = () => {
  const [employees, setEmployees] = useState([]);
  const [error, setError] = useState(null);
  const { user, isAuthenticated } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
    }
  }, [isAuthenticated, navigate]);

  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        const response = await api.get("/api/employees");
        setEmployees(response.data);
      } catch (err) {
        setError("Failed to fetch employees: Unauthorized or server error");
      }
    };
    if (isAuthenticated) {
      fetchEmployees();
    }
  }, [isAuthenticated]);

  if (error) {
    return <div className={styles.error}>{error}</div>;
  }

  return (
    <div className={styles.headerSection}>
      <h1 className={styles.title}>Employees</h1>
      {user?.roles?.includes("ADMIN") && (
        <Button
          type="primary"
          onClick={() => navigate("/add-employee")}
          className={styles.addButton}
        >
          Add New Employee
        </Button>
      )}
      {employees.length === 0 && (
        <p className={styles.noEmployees}>No employees available</p>
      )}
      {employees.length > 0 && (
        <div className={styles.cardsContainer}>
          {employees.map((employee) => (
            <SmallCard key={employee.id} employee={employee} />
          ))}
        </div>
      )}
    </div>
  );
};

export default SmallCardPage;