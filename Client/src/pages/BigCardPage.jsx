import { useState, useEffect } from "react";
import api from "../api/api";
import BigCard from "../components/BigCard";
import styles from "./BigCardPage.module.css";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";

const BigCardPage = () => {
  const [cards, setCards] = useState([]);
  const [error, setError] = useState(null);
  const { user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCards = async () => {
      try {
        const response = await api.get("/api/shops");
        setCards(response.data);
      } catch (err) {
        setError("Failed to fetch cards: Unauthorized");
      }
    };
    fetchCards();
  }, []);

  if (error) {
    return <div className={styles.error}>{error}</div>;
  }

  return (
    <div className={styles.headerSection}>
      <h1 className={styles.title}>Car services</h1>
      {cards.length === 0 && (
        <p className={styles.noShops}>No car services available</p>
      )}
      {user?.roles?.includes("ADMIN") && (
        <Button
          type="primary"
          onClick={() => navigate("/add-card")}
          className={styles.addButton}
        >
          Add New car service
        </Button>
      )}
      {cards.length > 0 && (
        <div className={styles.cardsContainer}>
          {cards.map((card) => (
            <BigCard key={card.id} card={card} />
          ))}
        </div>
      )}
    </div>
  );
};

export default BigCardPage;