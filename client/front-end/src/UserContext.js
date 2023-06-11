import React from "react";

export const UserContext = React.createContext();

export function UserContextContextProvider({
  children,
  user,
  role,
  setUser,
  setLoading,
}) {
  return (
    <UserContext.Provider value={{ user, role, setUser, setLoading }}>
      {children}
    </UserContext.Provider>
  );
}
