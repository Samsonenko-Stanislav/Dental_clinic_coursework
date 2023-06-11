export const saveToLocalStorage = (key, state, stringify = true) => {
  try {
    const serializedState = stringify ? JSON.stringify(state) : state;
    localStorage.setItem(key, serializedState);
  } catch (e) {
    console.log(e);
  }
};

export const loadFromLocalStorage = (key, parse = true) => {
  try {
    const serializedState = localStorage.getItem(key);
    if (serializedState === null) {
      return undefined;
    }
    return parse ? JSON.parse(serializedState) : serializedState;
  } catch (e) {
    return undefined;
  }
};

export const removeFromLocalStorage = (key) => {
  try {
    localStorage.removeItem(key);
  } catch (e) {
    console.log(e);
  }
};

export const saveToLocalStorageObjects = (designData, sideLength) => {
  const array = [];

  Object.keys(designData.projectSideParams).forEach((side, index) => {
    const sideJson = designData.projectSideParams?.[side].sideJson;

    const objects = JSON.parse(sideJson)
      .objects.filter((item) => item.name !== 'productImage')
      .map((item) => ({ ...item, localSaved: true }));

    if (objects.length && sideLength >= index + 1) {
      array.push({
        index,
        objects,
      });
    }
  });

  saveToLocalStorage(`objects`, array);
};
