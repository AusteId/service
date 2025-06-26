import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/api";
import { useAuth } from "../context/AuthContext";
import Button from "../components/Button";
import SmallCard from "../components/SmallCard";
import styles from "./SmallCardPage.module.css";

const OneBigCardPage = () => {
  const { shopId } = useParams();
  const [shop, setShop] = useState(null);
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
    const fetchData = async () => {
      try {
        const [shopResponse, employeesResponse] = await Promise.all([
          api.get(`/api/shops/${shopId}`),
          api.get(`/api/shops/${shopId}/employees`),
        ]);
        setShop(shopResponse.data);
        setEmployees(employeesResponse.data);
      } catch (err) {
        setError("Failed to fetch shop or employees: Unauthorized or server error");
      }
    };
    if (isAuthenticated) {
      fetchData();
    }
  }, [isAuthenticated, shopId]);

  if (error) {
    return <div className={styles.error}>{error}</div>;
  }

  if (!shop) {
    return <div>Loading...</div>;
  }

  return (
    <div className={styles.headerSection}>
      <h1 className={styles.title}>{shop.name}</h1>
      <div className={styles.shopDetails}>
        <p>Address: {shop.address}</p>
        <p>City: {shop.city}</p>
        <p>Phone: {shop.phone}</p>
        <p>Email: {shop.email}</p>
        <p>Working Hours: {shop.workingHours}</p>
        <p>Description: {shop.description || "No description"}</p>
        {shop.imagePath && (
          <img
            src={
              shop.imagePath.startsWith("http")
                ? shop.imagePath
                : `${import.meta.env.BASE_URL}assets/${shop.imagePath.replace(/^\/assets\//, "")}`
            }
            alt={shop.name}
            className={styles.shopImage}
          />
        )}
      </div>
      <h2 className={styles.subtitle}>Employees</h2>
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

export default OneBigCardPage;