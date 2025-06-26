const FieldValidationError = ({ children }) => {
    if (!children) return null;

    return (
        <div className="pl-[10rem] mt-0.5 absolute text-red-600 drop-shadow-lg max-w-92 text-center text-[0.75rem]/[0.875rem]">{children}</div>
    );
};
export default FieldValidationError;