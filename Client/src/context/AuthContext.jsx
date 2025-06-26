import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const auth = localStorage.getItem("auth");
    if (auth) {
      const { email, password, user: userData } = JSON.parse(auth);
      setUser({ email, id: userData.id, ...userData });
      setIsAuthenticated(true);
    }
  }, []);

  const login = (email, password, userData) => {
    setUser({ email, id: userData.id, ...userData });
    setIsAuthenticated(true);
    localStorage.setItem("auth", JSON.stringify({ email, password, user: userData }));
  };

  const logout = () => {
    setUser(null);
    setIsAuthenticated(false);
    localStorage.removeItem("auth");
  };

  return (
    <AuthContext.Provider value={{ user, isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);