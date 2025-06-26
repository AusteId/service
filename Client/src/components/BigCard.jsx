import styles from "./BigCard.module.css";
import Button from "./Button";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import { toast } from "react-toastify";

const BigCard = ({ card }) => {
    const { user, login } = useAuth();
    const navigate = useNavigate();
    const isAdmin = user?.roles?.includes("ADMIN");

    const handleDelete = async () => {
        try {
            await api.delete(`/api/shops/${card.id}`);
            toast.success("Card deleted successfully");
            window.location.reload();
        } catch (error) {
            toast.error(`Failed to delete card: ${error.response?.status || error.message}`);
            console.error("Delete error:", error);
        }
    };

    const imageSrc = card.imagePath.startsWith('http')
        ? card.imagePath
        : `${import.meta.env.BASE_URL}assets/${card.imagePath.replace(/^\/assets\//, '')}`;

    return (
        <div className={styles.card}>
            <div className={styles.content}>
                <h2 className={styles.name}>{card.name}</h2>
                <p className={styles.info}>Address: {card.address}</p>
                <p className={styles.info}>City: {card.city}</p>
                <p className={styles.info}>Phone: {card.phone}</p>
                <p className={styles.info}>Email: {card.email}</p>
                <p className={styles.info}>Working Hours: {card.workingHours}</p>
                <p className={styles.info}>Description: {card.description}</p>
                <div className={styles.buttonContainer}>
                    <Button type="primary" onClick={() => navigate(`/shops/${card.id}/employees`)}>
                        Employees
                    </Button>
                    {isAdmin && (
                        <>
                            <Button
                                type="primary"
                                onClick={() => navigate(`/update-card/${card.id}`)}
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
            <div className={styles.imageContainer}>
                <img src={imageSrc} alt={card.name} className={styles.image} />
            </div>
        </div>
    );
}

export default BigCard;