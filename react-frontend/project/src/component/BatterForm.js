import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faPlus,
  faCheckCircle,
  faTrashAlt,
} from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';

import styles from '../css/BatteryForm.module.css';

function BatteryForm() {
  const navigate = useNavigate();
  const [batteries, setBatteries] = useState([]);
  const [name, setName] = useState('');
  const [postcode, setPostcode] = useState('');
  const [capacity, setCapacity] = useState(0);

  const handleBatterySearch = () => {
    navigate('/battery-search');
  };

  const handleAddBattery = () => {
    if (!name && !postcode && !capacity) {
      return;
    }

    const newBattery = {
      name,
      postcode,
      capacity: parseInt(capacity, 10),
    };
    setBatteries([...batteries, newBattery]);
    setName('');
    setPostcode('');
    setCapacity(0);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log('batteries:', batteries);

    fetch('http://localhost:8080/batteries/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(
        batteries.map((battery) => ({
          name: battery.name,
          postcode: battery.postcode,
          capacity: battery.capacity,
        }))
      ),
    })
      .then((response) => {
        console.log(response);
        setBatteries([]);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Add Batteries</h1>

      <div
        className={styles.buttonContainer}
        style={{ position: 'absolute', top: '10px', right: '10px' }}
      >
        <button
          className={styles.button}
          type='button'
          onClick={handleBatterySearch}
        >
          Battery Search
        </button>
      </div>

      <form className={styles.form} onSubmit={handleSubmit}>
        <div className={styles.formInputs}>
          <div className={styles.inputContainer}>
            <label className={styles.label} htmlFor='name'>
              Battery Name:
              <input
                className={styles.input}
                type='text'
                id='name'
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </label>
          </div>
          <div className={styles.inputContainer}>
            <label className={styles.label} htmlFor='postcode'>
              Postcode:
              <input
                className={styles.input}
                type='text'
                id='postcode'
                value={postcode}
                onChange={(e) => setPostcode(e.target.value)}
              />
            </label>
          </div>
          <div className={styles.inputContainer}>
            <label className={styles.label} htmlFor='capacity'>
              Capacity (Ah):
              <input
                className={styles.input}
                type='number'
                id='capacity'
                value={capacity}
                onChange={(e) => setCapacity(e.target.value)}
              />
            </label>
          </div>
          <div className={styles.buttonContainer}>
            <button
              className={styles.button}
              type='button'
              onClick={handleAddBattery}
            >
              <FontAwesomeIcon icon={faPlus} />
              Add Battery
            </button>
          </div>
        </div>
        <div className={styles.batteries}>
          {batteries.length > 0 && (
            <>
              <h2 className={styles.batteriesTitle}>Added Batteries:</h2>
              <ul className={styles.batteriesList}>
                {batteries.map((battery, index) => (
                  <li key={battery.name} className={styles.batteriesItem}>
                    <div className={styles.nameContainer}>
                      <span className={styles.batteryName}>{battery.name}</span>
                      <button
                        className={styles.removeButton}
                        onClick={() =>
                          setBatteries(batteries.filter((_, i) => i !== index))
                        }
                        aria-label='Remove Battery'
                        type='button'
                      >
                        <FontAwesomeIcon icon={faTrashAlt} />
                      </button>
                    </div>
                    <div className={styles.batteryDetails}>
                      <div className={styles.batteryDetail}>
                        <span className={styles.batteryLabel}>Postcode:</span>
                        {battery.postcode}
                      </div>

                      <div className={styles.batteryDetail}>
                        <span className={styles.batteryLabel}>Capacity:</span>
                        {battery.capacity}
                      </div>
                    </div>
                  </li>
                ))}
              </ul>
            </>
          )}
        </div>
        <button className={styles.submit} type='submit'>
          <FontAwesomeIcon icon={faCheckCircle} />
          Submit
        </button>
      </form>
    </div>
  );
}

export default BatteryForm;
