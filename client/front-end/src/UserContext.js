import React from "react";

export const UserContext = React.createContext();

export function UserContextContextProvider({ children, user, role, setUser }) {
  return (
    <UserContext.Provider value={{ user, role, setUser }}>
      {children}
    </UserContext.Provider>
  );
}
