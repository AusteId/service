import { useForm } from "react-hook-form";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { useAuth } from "../context/AuthContext";
import Button from "../components/Button";
import api from "../api/api";
import styles from "./AddSmallCardForm.module.css";

const AddSmallCardForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
    setValue,
  } = useForm();
  const navigate = useNavigate();
  const { user, isAuthenticated } = useAuth();
  const [shops, setShops] = useState([]);
  const [searchParams] = useSearchParams();
  const defaultShopId = searchParams.get("shopId") || "";

  useEffect(() => {
    if (!isAuthenticated || !user?.roles?.includes("ADMIN")) {
      navigate("/login");
    }
  }, [isAuthenticated, user, navigate]);

  useEffect(() => {
    const fetchShops = async () => {
      try {
        const response = await api.get("/api/shops");
        setShops(response.data);
        if (defaultShopId) {
          setValue("shopId", defaultShopId);
        }
      } catch (error) {
        toast.error("Failed to fetch shops");
      }
    };
    if (isAuthenticated) {
      fetchShops();
    }
  }, [isAuthenticated, setValue, defaultShopId]);

  const onSubmit = async (data) => {
    try {
      await api.post(`/api/employees/${data.shopId}`, {
        firstname: data.firstname,
        lastname: data.lastname,
        birthDate: data.birthDate,
        address: data.address,
        phone: data.phone,
        email: data.email,
        city: data.city,
        specialization: data.specialization,
        description: data.description,
      });
      toast.success("Employee created successfully");
      reset();
      navigate(`/shops/${data.shopId}/employees`);
    } catch (error) {
      toast.error("Failed to create employee");
    }
  };

  return (
    <section className={styles.formContainer}>
      <h1 className={styles.title}>Add new employee</h1>
      <form className={styles.form} onSubmit={handleSubmit(onSubmit)}>
        <div className={styles.row}>
          <label htmlFor="firstname">Firstname</label>
          <input
            type="text"
            {...register("firstname", {
              required: "Firstname is required",
              minLength: { value: 2, message: "Firstname must be at least 2 characters" },
              maxLength: { value: 100, message: "Firstname must be less than 100 characters" },
              pattern: {
                value: /^[A-Za-zĄČĘĖĮŠŲŪŽąčęėįšųūž\s'-]+$/,
                message: "Firstname can only contain letters",
              },
            })}
          />
          {errors.firstname && <span className={styles.error}>{errors.firstname.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="lastname">Lastname</label>
          <input
            type="text"
            {...register("lastname", {
              required: "Lastname is required",
              minLength: { value: 2, message: "Lastname must be at least 2 characters" },
              maxLength: { value: 100, message: "Lastname must be less than 100 characters" },
              pattern: {
                value: /^[A-Za-zĄČĘĖĮŠŲŪŽąčęėįšųūž\s'-]+$/,
                message: "Lastname can only contain letters",
              },
            })}
          />
          {errors.lastname && <span className={styles.error}>{errors.lastname.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="phone">Phone</label>
          <input
            type="text"
            {...register("phone", {
              required: "Phone is required",
              minLength: { value: 4, message: "Phone must be at least 4 characters" },
              maxLength: { value: 15, message: "Phone must be less than 15 characters" },
            })}
          />
          {errors.phone && <span className={styles.error}>{errors.phone.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="email">Email</label>
          <input
            type="email"
            {...register("email", {
              required: "Email is required",
              pattern: {
                value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                message: "Invalid email format",
              },
            })}
          />
          {errors.email && <span className={styles.error}>{errors.email.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="city">City</label>
          <input
            type="text"
            {...register("city", {
              required: "City is required",
              minLength: { value: 2, message: "City must be at least 2 characters" },
              maxLength: { value: 100, message: "City must be less than 100 characters" },
            })}
          />
          {errors.city && <span className={styles.error}>{errors.city.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="specialization">Specialization</label>
          <input
            type="text"
            {...register("specialization", {
              required: "Specialization is required",
              minLength: { value: 2, message: "Specialization must be at least 2 characters" },
              maxLength: { value: 500, message: "Specialization must be less than 500 characters" },
            })}
          />
          {errors.specialization && <span className={styles.error}>{errors.specialization.message}</span>}
        </div>
        <div className={styles.row}>
          <label htmlFor="shopId">Car Service</label>
          <select
            {...register("shopId", { required: "Car service is required" })}
          >
            <option value="">Select the car service</option>
            {shops.map((shop) => (
              <option key={shop.id} value={shop.id}>
                {shop.name}
              </option>
            ))}
          </select>
          {errors.shopId && <span className={styles.error}>{errors.shopId.message}</span>}
        </div>
        <Button type="primary">Add Employee</Button>
      </form>
    </section>
  );
};

export default AddSmallCardForm;