import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const PrivateRoute = ({ component }) => {
  const { isAuthenticated } = useAuth();

  return isAuthenticated ? component : <Navigate to="/login" />;
};

export default PrivateRoute;