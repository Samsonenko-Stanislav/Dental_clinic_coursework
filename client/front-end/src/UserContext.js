import React from 'react';

export const UserContext = React.createContext();

export function UserContextContextProvider({ children, user, role }) {
  return <UserContext.Provider value={{ user, role }}>{children}</UserContext.Provider>;
}
