import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/api";
import { useAuth } from "../context/AuthContext";
import Button from "../components/Button";
import SmallCard from "../components/SmallCard";
import styles from "./SmallCardPage.module.css";

const ShopEmployeesPage = () => {
  const { shopId } = useParams();
  const [employees, setEmployees] = useState([]);
  const [shopName, setShopName] = useState("");
  const [error, setError] = useState(null);
  const { user, isAuthenticated } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
    }
  }, [isAuthenticated, navigate]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [employeesResponse, shopResponse] = await Promise.all([
          api.get(`/api/shops/${shopId}/employees`),
          api.get(`/api/shops/${shopId}`),
        ]);
        setEmployees(employeesResponse.data);
        setShopName(shopResponse.data.name);
      } catch (err) {
        setError("Failed to fetch employees or shop: Unauthorized or server error");
      }
    };
    if (isAuthenticated) {
      fetchData();
    }
  }, [isAuthenticated, shopId]);

  if (error) {
    return <div className={styles.error}>{error}</div>;
  }

  return (
    <div className={styles.headerSection}>
      <h1 className={styles.title}>Employees for {shopName || "Shop"}</h1>
      {user?.roles?.includes("ADMIN") && (
        <Button
          type="primary"
          onClick={() => navigate(`/add-employee?shopId=${shopId}`)}
          className={styles.addButton}
        >
          Add New Employee
        </Button>
      )}
      {employees.length === 0 && (
        <p className={styles.noEmployees}>No employees available for this shop</p>
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

export default ShopEmployeesPage;