import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import BatterySearch from './css/BatterySearch';

describe('BatterySearch component', () => {
  test('renders search form with labels and input fields', () => {
    render(<BatterySearch />);
    const startPostcodeLabel = screen.getByLabelText('Start Postcode:');
    const endPostcodeLabel = screen.getByLabelText('End Postcode:');
    const startPostcodeInput = screen.getByRole('textbox', {
      name: 'Start Postcode:',
    });
    const endPostcodeInput = screen.getByRole('textbox', {
      name: 'End Postcode:',
    });
    const searchButton = screen.getByRole('button', { name: 'Search' });
    expect(startPostcodeLabel).toBeInTheDocument();
    expect(endPostcodeLabel).toBeInTheDocument();
    expect(startPostcodeInput).toBeInTheDocument();
    expect(endPostcodeInput).toBeInTheDocument();
    expect(searchButton).toBeInTheDocument();
  });

  test('sets start and end postcodes in state when input fields are updated', () => {
    render(<BatterySearch />);
    const startPostcodeInput = screen.getByRole('textbox', {
      name: 'Start Postcode:',
    });
    const endPostcodeInput = screen.getByRole('textbox', {
      name: 'End Postcode:',
    });
    fireEvent.change(startPostcodeInput, { target: { value: 'AB1 2CD' } });
    fireEvent.change(endPostcodeInput, { target: { value: 'XY3 4ZW' } });
    expect(startPostcodeInput).toHaveValue('AB1 2CD');
    expect(endPostcodeInput).toHaveValue('XY3 4ZW');
  });

  test('calls handleSearch function when search button is clicked', () => {
    const handleSearch = jest.fn();
    render(<BatterySearch handleSearch={handleSearch} />);
    const searchButton = screen.getByRole('button', { name: 'Search' });
    fireEvent.click(searchButton);
    expect(handleSearch).toHaveBeenCalledTimes(1);
  });

  test('renders results table with correct data when batteryDetails is not null', () => {
    const batteryDetails = {
      count: 2,
      batteryNames: ['Battery 1', 'Battery 2'],
      totalCapacity: 200,
      averageCapacity: 100,
    };
    render(<BatterySearch batteryDetails={batteryDetails} />);
    const countCell = screen.getByText('2');
    const batteryNamesCell = screen.getByText('Battery 1, Battery 2');
    const totalCapacityCell = screen.getByText('200');
    const averageCapacityCell = screen.getByText('100');
    expect(countCell).toBeInTheDocument();
    expect(batteryNamesCell).toBeInTheDocument();
    expect(totalCapacityCell).toBeInTheDocument();
    expect(averageCapacityCell).toBeInTheDocument();
  });

  test('does not render results table when batteryDetails is null', () => {
    render(<BatterySearch />);
    const table = screen.queryByRole('table');
    expect(table).not.toBeInTheDocument();
  });
});
