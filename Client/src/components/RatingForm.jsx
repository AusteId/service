import { FaStar, FaRegStar } from "react-icons/fa";
import { useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { useAuth } from "../context/AuthContext";
import Button from "../components/Button";
import api from "../api/api";
import styles from "./RatingForm.module.css";

const RatingForm = () => {

    const {
        register,
        handleSubmit,
        setValue,
        formState: { errors },
    } = useForm({ defaultValues: { rating: 0 } });
    const [hoverRating, setHoverRating] = useState(0);
    const navigate = useNavigate();
    const { employeeId } = useParams();
    const { user, isAuthenticated } = useAuth();
    const [employeeName, setEmployeeName] = useState("");
    const [rating, setRating] = useState(0);

    useEffect(() => {
        if (!isAuthenticated || !user?.roles?.includes("USER")) {
            navigate("/login");
        }
        const fetchEmployeeName = async () => {
            try {
                const response = await api.get(`/api/employees/${employeeId}`);
                setEmployeeName(`${response.data.firstname} ${response.data.lastname}`);
            } catch (error) {
                toast.error("Failed to fetch employee name");
            }
        };
        fetchEmployeeName();
    }, [isAuthenticated, user, navigate, employeeId]);

    const onSubmit = async (data) => {
        try {
            const raterId = user?.id;
            if (!raterId) throw new Error("User ID not found");
            await api.post(`/api/employees/${employeeId}/ratings`, {
                rating: parseInt(data.rating, 10),
                employeeId: parseInt(employeeId, 10),
                raterId: raterId,
            }, { headers: { "Content-Type": "application/json" } });
            toast.success("Rating submitted successfully");
            navigate("/employees");
        } catch (error) {
            toast.error(`Failed to submit rating: ${error.response?.data?.message || error.message}`);
            console.error("Error:", error.response?.data);
        }
    };

    const handleRating = (value) => {
        setValue("rating", value);
    };

    const handleClose = () => {
        navigate("/employees");
    };

    return (
        <div className={styles.modalOverlay}>
            <div className={styles.modalContent}>
                <button onClick={handleClose} className={styles.closeButton}>
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="h-6 w-6"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                    >
                        <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth={2}
                            d="M6 18L18 6M6 6l12 12"
                        />
                    </svg>
                </button>
                <h2 className={styles.title}>Rate Employee: {employeeName}</h2>
                <p className={styles.description}>
                    How would you rate {employeeName}?
                </p>
                <div className={styles.starContainer}>
                    {[1, 2, 3, 4, 5].map((star) => (
                        <button
                            key={star}
                            onClick={() => handleRating(star)}
                            onMouseEnter={() => setHoverRating(star)}
                            onMouseLeave={() => setHoverRating(0)}
                            className={styles.starButton}
                        >
                            {star <= (hoverRating || register("rating").value) ? (
                                <FaStar className={styles.starIcon} />
                            ) : (
                                <FaRegStar className={styles.starIcon} />
                            )}
                        </button>
                    ))}
                </div>
                   {register("rating").value > 0 && (
                    <p className={styles.ratingText}>
                        You selected {rating} {rating === 1 ? "star" : "stars"}
                    </p>
                )}
                <div className={styles.buttonContainer}>
                    <Button type="primary" onClick={handleClose}>
                        Cancel
                    </Button>
                    <Button
                        type="primary"
                        onClick={handleSubmit(onSubmit)}
                        className={styles.submitButton}
                        disabled={!register("rating").value}
                    >
                        Submit
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default RatingForm;