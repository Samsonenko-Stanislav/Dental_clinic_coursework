import React, {memo, useContext, useState} from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { createUser } from '../store/slice/UserSlice';
import { showNotification } from '../App';
import {UserContext} from "../context/UserContext";

const AddNewUser = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [isAdmin, setIsAdmin] = useState(false);
  const [isDoctor, setIsDoctor] = useState(false);
  const [isUser, setIsUser] = useState(false);
  const [isMonday, setIsMonday] = useState(false);
  const [isTuesday, setIsTuesday] = useState(false);
  const [isWednesday, setIsWednesday] = useState(false);
  const [isThursday, setIsThursday] = useState(false);
  const [isFriday, setIsFriday] = useState(false);
  const [isSaturday, setIsSaturday] = useState(false);
  const [isSunday, setIsSunday] = useState(false);
  const [username, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [gender, setGender] = useState('MALE');
  const [fullName, setFullName] = useState('');

  const [jobTitle, setJobTitle] = useState('');
  const [workStart, setWorkStart] = useState('');
  const [workEnd, setWorkEnd] = useState('');
  const [durationApp, setDurationApp] = useState('');

  const { setLoading } = useContext(UserContext);

  const handleAdminChange = (event) => {
    setIsAdmin(event.target.checked);
  };

  const handleDoctorChange = (event) => {
    setIsDoctor(event.target.checked);
  };

  const handleUserChange = (event) => {
    setIsUser(event.target.checked);
  };

  const handleMondayChange = (event) => {
    setIsMonday(event.target.checked)
  }

  const handleTuesdayChange = (event) => {
    setIsTuesday(event.target.checked)
  }
  const handleWednesdayChange = (event) => {
    setIsWednesday(event.target.checked)
  }
  const handleThursdayChange = (event) => {
    setIsThursday(event.target.checked)
  }
  const handleFridayChange = (event) => {
    setIsFriday(event.target.checked)
  }
  const handleSaturdayChange = (event) => {
    setIsSaturday(event.target.checked)
  }
  const handleSundayChange = (event) => {
    setIsSunday(event.target.checked)
  }


  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    const roles = [];
    if (isUser) roles.push('USER');
    if (isDoctor) roles.push('DOCTOR');
    if (isAdmin) roles.push('ADMIN');
    const workDays = [];

    if(isMonday) workDays.push('MONDAY');
    if(isTuesday) workDays.push('TUESDAY');
    if(isWednesday) workDays.push('WEDNESDAY');
    if(isThursday) workDays.push('THURSDAY');
    if(isFriday) workDays.push('FRIDAY');
    if(isSaturday) workDays.push('SATURDAY');
    if(isSunday) workDays.push('SUNDAY');



    if (!isUser && !isDoctor && !isAdmin) {
      setLoading(false)
      showNotification('error', 'Выберите хоть одну роль', 'Ошибка');
      return;
    }

    const response = await dispatch(
      createUser({
        newData: {
          password,
          username,
          email,
          gender,
          fullName,
          roles,
          jobTitle,
          workStart: !workStart ? null : workStart + ':00',
          workEnd: !workEnd ? null : workEnd + ':00',
          workDays,
          durationApp,
        },
      })
    );

    if (response?.type?.includes('fulfilled')) {
      setLoading(false)
      navigate('/user')
    }
    ;
  };

  return (
    <div className="col-md-7 col-lg-8">
      <h4 className="my-4">Создать нового пользователя</h4>
      <form onSubmit={handleSubmit}>
        <div className="row">
          <div className="row col-12">
            <div className="col-6">
              <label htmlFor="username" className="form-label">
                Логин
              </label>
              <input type="text" name="username" className="form-control" id="username" required value={username} onChange={(e) => setUserName(e.target.value)} />
            </div>
            <div className="col-6">
              <label htmlFor="password" className="form-label">
                Пароль
              </label>
              <input type="password" name="password" className="form-control" id="password" required value={password} onChange={(e) => setPassword(e.target.value)} />
            </div>
          </div>
          <div className="col">
            <div className="col-md-5 my-2">
              <input type="checkbox" name="ADMIN" id="ADMIN" checked={isAdmin} onChange={handleAdminChange} />
              <label htmlFor="ADMIN" className="form-label">
                Администратор (ADMIN)
              </label>
            </div>
            <div className="col-md-5 mb-2">
              <input type="checkbox" name="DOCTOR" id="DOCTOR" checked={isDoctor} onChange={handleDoctorChange} />
              <label htmlFor="DOCTOR" className="form-label">
                Врач (DOCTOR)
              </label>
            </div>
            <div className="col-md-5 mb-2">
              <input type="checkbox" name="USER" id="USER" checked={isUser} onChange={handleUserChange} />
              <label htmlFor="USER" className="form-label">
                Пациент (USER)
              </label>
            </div>
          </div>
        </div>
        <div className="row col-12" id="forNotAdmin">
          {(isDoctor || isUser) && (
            <div className="col-6">
              <label htmlFor="fullName" className="form-label">
                ФИО
              </label>
              <input type="text"  name="fullName" className="form-control" required id="fullName" value={fullName} onChange={(e) => setFullName(e.target.value)} />
            </div>
          )}
        </div>
        {isDoctor && (
          <div id="forEmpl">
            <div className="col-6">
              <label htmlFor="jobTitle" className="form-label">
                Должность
              </label>
              <input type="text" name="jobTitle" className="form-control" required id="jobTitle" value={jobTitle} onChange={(e) => setJobTitle(e.target.value)} />
            </div>
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="workStart" className="form-label">
                  Старт рабочего дня
                </label>
                <input type="time" name="workStart" className="form-control" required id="workStart" value={workStart} onChange={(e) => setWorkStart(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="workEnd" className="form-label">
                  Конец рабочего дня
                </label>
                <input type="time" name="workEnd" className="form-control" required id="workEnd" value={workEnd} onChange={(e) => setWorkEnd(e.target.value)} />
              </div>
              <div className="col-6">
                <label htmlFor="durationApp" className="form-label">
                  Время приема (минут)
                </label>
                <input type="text" name="durationApp" className="form-control" required id="durationApp" value={durationApp} onChange={(e) => setDurationApp(e.target.value)} />
              </div>
              <div className="col">
                <div className="col-md-5 my-2">
                  <input type="checkbox" name="MONDAY" id="MONDAY" checked={isMonday} onChange={handleMondayChange} />
                  <label htmlFor="MONDAY" className="form-label">
                    Понедельник
                  </label>
                </div>
                <div className="col-md-5 my-2">
                  <input type="checkbox" name="TUESDAY" id="TUESDAY" checked={isTuesday} onChange={handleTuesdayChange} />
                  <label htmlFor="TUESDAY" className="form-label">
                    Вторник
                  </label>
                </div>
                <div className="col-md-5 my-2">
                  <input type="checkbox" name="WEDNESDAY" id="WEDNESDAY" checked={isWednesday} onChange={handleWednesdayChange} />
                  <label htmlFor="WEDNESDAY" className="form-label">
                    Среда
                  </label>
                </div>
                <div className="col-md-5 my-2">
                  <input type="checkbox" name="THURSDAY" id="THURSDAY" checked={isThursday} onChange={handleThursdayChange} />
                  <label htmlFor="THURSDAY" className="form-label">
                    Четверг
                  </label>
                </div><div className="col-md-5 my-2">
                <input type="checkbox" name="FRIDAY" id="FRIDAY" checked={isFriday} onChange={handleFridayChange} />
                <label htmlFor="FRIDAY" className="form-label">
                  Пятница
                </label>
              </div><div className="col-md-5 my-2">
                <input type="checkbox" name="SATURDAY" id="SATURDAY" checked={isSaturday} onChange={handleSaturdayChange} />
                <label htmlFor="SATURDAY" className="form-label">
                  Суббота
                </label>
              </div><div className="col-md-5 my-2">
                <input type="checkbox" name="SUNDAY" id="SUNDAY" checked={isSunday} onChange={handleSundayChange} />
                <label htmlFor="SUNDAY" className="form-label">
                  Воскресенье
                </label>
              </div>
              </div>
            </div>
          </div>
        )}
        {isUser && (
          <div id="forUser">
            <div className="col-6">
              <label htmlFor="email" className="form-label">
                e-mail
              </label>
              <input type="email" name="email" className="form-control" id="email" value={email} onChange={(e) => setEmail(e.target.value)} />
            </div>
            <div className="row col-12">
              <div className="col-6">
                <input type="radio" id="MALE" name="gender" value={'MALE'} onChange={() => setGender('MALE')} />
                <label htmlFor="MALE">Мужской</label>
              </div>
              <div className="my-2">
                <input type="radio" id="FEMALE" name="gender" value={'FEMALE'} onChange={() => setGender('FEMALE')} />
                <label htmlFor="FEMALE">Женский</label>
              </div>
            </div>
          </div>
        )}
        <button className="w-100 btn btn-primary btn-lg my-4" type="submit">
          Создать
        </button>
      </form>
    </div>
  );
};

export default memo(AddNewUser);
