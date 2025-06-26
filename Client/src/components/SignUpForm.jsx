import { useForm } from "react-hook-form";
import Button from "./Button";
import { useState } from "react";
import FieldValidationError from "./FieldValidationError";
import axios from "axios";
import { toast } from "react-toastify";
import styles from "./SignUpForm.module.css";
import { Link } from "react-router-dom";

const SignUpForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
    reset,
  } = useForm();

  const [serverError, setServerError] = useState("");

  const password = watch("password");

  const onSubmit = async (data) => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/users/register",
        {
          email: data.email,
          password: data.password,
        }
      );

      setServerError("");
      reset();
      toast.success("Registration successful");
    } catch (error) {
      if (error.response) {
        setServerError(error.response.data.message || "You are not registered");
      } else {
        setServerError("Server error. Try again later");
      }
    }
  };

  return (
    <section className={styles.signup}>
      

      {serverError && <div style={{ color: "red" }}>{serverError}</div>}

      <form className={styles.form} onSubmit={handleSubmit(onSubmit)}>
      <h1 className="text-center text-[--color-light--2] text-xl font-medium">Create an account</h1>
        <div className={styles.row}>
          <label htmlFor="email" className="text-[--color-light--2]">Email address</label>
          <input
            type="email"
            placeholder="name@example.com"
            className={styles.input}
            {...register("email", {
              required: "Can't be empty",
              pattern: {
                value:
                  /^(?=.{3,254}$)(?=.{1,64}@)(?!\.)(?!.*\.\.)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]{1,253}\.[A-Za-z]{2,}$/,
                message: "Invalid email address",
              },
            })}
          />
          <FieldValidationError>{errors.email?.message}</FieldValidationError>
        </div>

        <div className={styles.row}>
          <label htmlFor="password" className="text-[--color-light--2]">Password</label>
          <input
            type="password"
            placeholder="Enter your password"
            className={styles.input}
            {...register("password", {
              required: "Can't be empty",
              minLength: {
                value: 8,
                message: "Password must be at least 8 characters long",
              },
              pattern: {
                value:
                  /^(?!.*<.*?>)(?!.*javascript:)(?=.*[a-z])(?=.*[A-Z]).{8,}$/,
                message:
                  "Password must contain both uppercase and lowercase letters",
              },
            })}
          />
          <FieldValidationError>
            {errors.password?.message}
          </FieldValidationError>
        </div>

        <div className={styles.row}>
          <label htmlFor="password" className="text-[--color-light--2]">Confirm password</label>
          <input
            type="password"
            placeholder="Repeat your password"
            className={styles.input}
            {...register("repeatPassword", {
              required: "Can't be empty",
              validate: (value) =>
                value === password || "Passwords don't match",
              minLength: {
                value: 8,
                message: "Password must be at least 8 characters long",
              },
              pattern: {
                value:
                  /^(?!.*<.*?>)(?!.*javascript:)(?=.*[a-z])(?=.*[A-Z]).{8,}$/,
                message:
                  "Password must contain both uppercase and lowercase letters",
              },
            })}
          />
          <FieldValidationError>
            {errors.repeatPassword?.message}
          </FieldValidationError>
        </div>

        <Button type="primary">Sign up</Button>
        <div>
        <p className="text-center text-[--color-light--2]">Already have an account?<span><Link to="/" className="text-[--color-brand--2]"> Login</Link></span></p>
      </div>
      </form>
    </section>
  );
};

export default SignUpForm;
