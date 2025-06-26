import SignUpPage from "./pages/SignUpPage";
import { Routes, Route } from "react-router-dom";
import LoginForm from "./components/LoginForm";
import PageNotFound from "./components/PageNotFound";
import BigCardPage from "./pages/BigCardPage";
import AddCardForm from "./components/AddCardForm";
import UpdateCardForm from "./components/UpdateCardForm";
import SmallCardPage from "./pages/SmallCardPage";
import AddSmallCardForm from "./components/AddSmallCardForm";
import UpdateSmallCardForm from "./components/UpdateSmallCardForm";
import ShopEmployeesPage from "./pages/ShopEmployeesPage";
import OneBigCardPage from "./pages/OneBigCardPage";
import RatingForm from "./components/RatingForm";
import PrivateRoute from "./components/PrivateRoute";
import NavBar from "./components/NavBar";

function App() {
  return (
    <>
      <NavBar />
      <Routes>
        <Route index element={<LoginForm />} />
        <Route path="/login" element={<LoginForm />} />
        <Route path="register" element={<SignUpPage />} />
        <Route
          path="/bigCards"
          element={<PrivateRoute component={<BigCardPage />} />}
        />
        <Route
          path="/add-card"
          element={<PrivateRoute component={<AddCardForm />} />}
        />
        <Route
          path="/update-card/:id"
          element={<PrivateRoute component={<UpdateCardForm />} />}
        />
        <Route
          path="/employees"
          element={<PrivateRoute component={<SmallCardPage />} />}
        />
        <Route
          path="/add-employee"
          element={<PrivateRoute component={<AddSmallCardForm />} />}
        />
        <Route
          path="/update-employee/:id"
          element={<PrivateRoute component={<UpdateSmallCardForm />} />}
        />
        <Route
          path="/shops/:shopId/employees"
          element={<PrivateRoute component={<ShopEmployeesPage />} />}
        />
        <Route
          path="/shops/:shopId"
          element={<PrivateRoute component={<OneBigCardPage />} />}
        />
        <Route
          path="/rate-employee/:employeeId"
          element={<PrivateRoute component={<RatingForm />} />}
        />
        <Route path="*" element={<PageNotFound />} />
      </Routes>
    </>
  );
}

export default App;
