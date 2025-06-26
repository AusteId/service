import { useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect } from "react";
import { toast } from "react-toastify";
import Button from "./Button";
import api from "../api/api";
import styles from "./AddCardForm.module.css";

const UpdateCardForm = () => {
  const { id } = useParams();
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCard = async () => {
      try {
        const response = await api.get(`/api/shops/${id}`);
        reset(response.data);
      } catch (error) {
        toast.error("Failed to fetch card");
      }
    };
    fetchCard();
  }, [id, reset]);

  const onSubmit = async (data) => {
    try {
      await api.put(`/api/shops/${id}`, data);
      toast.success("Card updated successfully");
      navigate("/bigCards");
    } catch (error) {
      toast.error("Failed to update card");
    }
  };

  return (
    <section className={styles.formContainer}>
      <h1 className={styles.title}>Update car service details</h1>
      <form className={styles.form} onSubmit={handleSubmit(onSubmit)}>
        <div className={styles.row}>
          <label htmlFor="name">Name</label>
          <input
            type="text"
            {...register("name", { required: "Name is required" })}
          />
          {errors.name && <span className={styles.error}>{errors.name.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="address">Address</label>
          <input
            type="text"
            {...register("address", { required: "Address is required" })}
          />
          {errors.address && <span className={styles.error}>{errors.address.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="city">City</label>
          <input
            type="text"
            {...register("city", { required: "City is required" })}
          />
          {errors.city && <span className={styles.error}>{errors.city.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="phone">Phone</label>
          <input
            type="text"
            {...register("phone", { required: "Phone is required" })}
          />
          {errors.phone && <span className={styles.error}>{errors.phone.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="email">Email</label>
          <input
            type="email"
            {...register("email", { required: "Email is required" })}
          />
          {errors.email && <span className={styles.error}>{errors.email.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="workingHours">Working Hours</label>
          <input
            type="text"
            {...register("workingHours", { required: "Working hours are required" })}
          />
          {errors.workingHours && <span className={styles.error}>{errors.workingHours.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="description">Car service manager</label>
          <textarea
            {...register("description", { required: "Description is required" })}
          />
          {errors.description && <span className={styles.error}>{errors.description.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="imagePath">Image URL</label>
          <input
            type="text"
            {...register("imagePath", { required: "Image URL is required" })}
          />
          {errors.imagePath && <span className={styles.error}>{errors.imagePath.message}</span>}
        </div>
        <Button type="primary">Update Card</Button>
      </form>
    </section>
  );
};

export default UpdateCardForm;