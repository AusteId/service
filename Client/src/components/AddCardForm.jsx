import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import Button from "./Button";
import api from "../api/api";
import styles from "./AddCardForm.module.css";

const AddCardForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm();
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      await api.post("/api/shops", data);
      toast.success("Card created successfully");
      reset();
      navigate("/bigCards");
    } catch (error) {
      toast.error("Failed to create card");
    }
  };

  return (
    <section className={styles.formContainer}>
      <h1 className={styles.title}>Add new car service</h1>
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
            {...register("description", { required: "Car servise manager is required" })}
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
        <Button type="primary">Add car service</Button>
      </form>
    </section>
  );
};

export default AddCardForm;