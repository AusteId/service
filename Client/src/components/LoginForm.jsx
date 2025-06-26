import { useForm } from "react-hook-form";
import Button from "./Button";
import { useState } from "react";
import FieldValidationError from "./FieldValidationError";
import { toast } from "react-toastify";
import styles from "./SignUpForm.module.css";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/api";
import { useAuth } from "../context/AuthContext";

const LoginForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm();

  const [serverError, setServerError] = useState("");
  const navigate = useNavigate();
  const { login } = useAuth();

  const onSubmit = async (data) => {
    try {
      const credentials = btoa(`${data.email}:${data.password}`);
      const response = await api.get("/api/users/me", {
        headers: {
          Authorization: `Basic ${credentials}`,
        },
      });

      login(data.email, data.password, response.data);
      setServerError("");
      reset();
      toast.success("Login successful");
      navigate("/bigCards");
    } catch (error) {
      setServerError("Invalid email or password");
    }
  };

  return (
    <section className={styles.signup}>
      {serverError && <div style={{ color: "red" }}>{serverError}</div>}

      <form className={styles.form} onSubmit={handleSubmit(onSubmit)}>
        <h1 className="text-center text-[--color-light--2] text-xl font-medium">
          Login to your account
        </h1>
        <div className={styles.row}>
          <label htmlFor="email" className="text-[--color-light--2]">
            Email address
          </label>
          <input
            type="email"
            placeholder="name@example.com"
            className={styles.input}
            {...register("email", {
              required: "Email is required",
            })}
          />
          <FieldValidationError>{errors.email?.message}</FieldValidationError>
        </div>

        <div className={styles.row}>
          <label htmlFor="password" className="text-[--color-light--2]">
            Password
          </label>
          <input
            type="password"
            placeholder="Enter your password"
            className={styles.input}
            {...register("password", {
              required: "Password is required",
            })}
          />
          <FieldValidationError>
            {errors.password?.message}
          </FieldValidationError>
        </div>

        <Button type="primary">Login</Button>
        <div>
          <p className="text-center text-[--color-light--2]">
            Don't have an account?&nbsp;
            <span>
              <Link to="/register" className="text-[--color-brand--2]">
                Sign Up
              </Link>
            </span>
          </p>
        </div>
      </form>
    </section>
  );
};

export default LoginForm;
