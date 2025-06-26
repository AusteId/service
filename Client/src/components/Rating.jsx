import { useState, useEffect } from "react";
import api from "../api/api";
import styles from "./SmallCard.module.css";

const Rating = ({ employeeId }) => {
  const [ratingData, setRatingData] = useState({
    averageRating: null,
    ratingCount: 0,
  });
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchRating = async () => {
      try {
        const response = await api.get(`/api/employees/${employeeId}/ratings/rater`);
        setRatingData({
          averageRating: response.data.averageRating,
          ratingCount: response.data.ratingCount,
        });
      } catch (err) {
        setError("Failed to fetch rating");
      }
    };
    fetchRating();
  }, [employeeId]);

  if (error) {
    return <span className={styles.info}>{error}</span>;
  }

  const stars = "★★★★★";
  const filledStars = Math.round(ratingData.averageRating || 0);
  const displayStars = stars.slice(0, filledStars).padEnd(5, "☆");
  
  const ratingText = ratingData.averageRating
    ? `${ratingData.averageRating.toFixed(1)} (${ratingData.ratingCount} reviews)`
    : "No ratings yet";

  return (
    <span className={styles.info}>
      {stars} {ratingText}
    </span>
  );
};

export default Rating;