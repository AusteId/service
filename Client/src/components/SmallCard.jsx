import { useState, useEffect } from "react";
import styles from "./SmallCard.module.css";
import Button from "../components/Button";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import { toast } from "react-toastify";
import Rating from "./Rating";

const SmallCard = ({ employee }) => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const isAdmin = user?.roles?.includes("ADMIN");
  const [ratingData, setRatingData] = useState({
    averageRating: employee.averageRating,
    ratingCount: employee.ratingCount,
  });

  const handleDelete = async () => {
    try {
      await api.delete(`/api/employees/${employee.id}`);
      toast.success("Employee deleted successfully");
      window.location.reload();
    } catch (error) {
      toast.error(`Failed to delete employee: ${error.response?.status || error.message}`);
      console.error("Delete error:", error);
    }
  };

  useEffect(() => {
    const fetchRating = async () => {
      try {
        const response = await api.get(`/api/employees/${employee.id}/ratings/rater`);
        setRatingData({
          averageRating: response.data.averageRating,
          ratingCount: response.data.ratingCount,
        });
      } catch (error) {
        console.error("Failed to fetch rating:", error);
      }
    };
    fetchRating();
  }, [employee.id]);

  return (
    <div className={styles.card}>
      <div className={styles.content}>
        <h2 className={styles.name}>
          {employee.firstname} {employee.lastname}
        </h2>
        <div className={styles.ratingContainer}>
          <Rating employeeId={employee.id} />
          <Button
            type="primary"
            onClick={() => navigate(`/rate-employee/${employee.id}`)}
            className={styles.rateButton}
          >
            Rate
          </Button>
        </div>
        <p className={styles.info}>Phone: {employee.phone}</p>
        <p className={styles.info}>Email: {employee.email}</p>
        <p className={styles.info}>Specialization: {employee.specialization}</p>
        <p className={styles.info}>Description: {employee.description || "No description"}</p>
        <p className={styles.info}>Car service: {employee.shop?.name || "Unknown"}</p>
        <div className={styles.buttonContainer}>
          <Button
            type="primary"
            onClick={() => navigate(`/shops/${employee.shop?.id}`)}
          >
            Car service
          </Button>
          {isAdmin && (
            <>
              <Button
                type="primary"
                onClick={() => navigate(`/update-employee/${employee.id}`)}
              >
                Update
              </Button>
              <Button type="primary" onClick={handleDelete}>
                Delete
              </Button>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default SmallCard;