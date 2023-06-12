import React from "react";

export const UserContext = React.createContext();

export function UserContextContextProvider({
  children,
  user,
  role,
  setLoading,
}) {
  return (
    <UserContext.Provider value={{ user, role, setLoading }}>
      {children}
    </UserContext.Provider>
  );
}
