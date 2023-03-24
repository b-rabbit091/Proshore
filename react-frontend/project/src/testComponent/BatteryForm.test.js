import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import BatteryForm from '../component/BatterForm';
import { BrowserRouter as Router } from 'react-router-dom';
describe('BatteryForm', () => {
  it('renders the form correctly', () => {
    render(<BatteryForm />);

    expect(screen.getByText('Add Batteries')).toBeInTheDocument();
    expect(screen.getByLabelText('Battery Name:')).toBeInTheDocument();
    expect(screen.getByLabelText('Postcode:')).toBeInTheDocument();
    expect(screen.getByLabelText('Capacity (Ah):')).toBeInTheDocument();
    expect(screen.getByText('Battery Search')).toBeInTheDocument();
    expect(screen.getByText('Submit')).toBeInTheDocument();
  });

  it('adds a battery to the list', () => {
    render(<BatteryForm />);

    fireEvent.change(screen.getByLabelText('Battery Name:'), {
      target: { value: 'Battery 1' },
    });
    fireEvent.change(screen.getByLabelText('Postcode:'), {
      target: { value: '12345' },
    });
    fireEvent.change(screen.getByLabelText('Capacity (Ah):'), {
      target: { value: '100' },
    });
    fireEvent.click(screen.getByText('Add Battery'));

    expect(screen.getByText('Added Batteries:')).toBeInTheDocument();
    expect(screen.getByText('Battery 1')).toBeInTheDocument();
    expect(screen.getByText('12345')).toBeInTheDocument();
    expect(screen.getByText('100')).toBeInTheDocument();
  });

  it('removes a battery from the list', () => {
    render(
      <Router>
        <BatteryForm />
      </Router>
    );

    fireEvent.change(screen.getByLabelText('Battery Name:'), {
      target: { value: 'Battery 1' },
    });
    fireEvent.change(screen.getByLabelText('Postcode:'), {
      target: { value: '12345' },
    });
    fireEvent.change(screen.getByLabelText('Capacity (Ah):'), {
      target: { value: '100' },
    });
    fireEvent.click(screen.getByText('Add Battery'));

    fireEvent.change(screen.getByLabelText('Battery Name:'), {
      target: { value: 'Battery 2' },
    });
    fireEvent.change(screen.getByLabelText('Postcode:'), {
      target: { value: '67890' },
    });
    fireEvent.change(screen.getByLabelText('Capacity (Ah):'), {
      target: { value: '200' },
    });
    fireEvent.click(screen.getByText('Add Battery'));

    expect(screen.getAllByText('Remove Battery')).toHaveLength(2);

    fireEvent.click(screen.getAllByText('Remove Battery')[0]);

    expect(screen.queryByText('Battery 1')).not.toBeInTheDocument();
    expect(screen.queryByText('12345')).not.toBeInTheDocument();
    expect(screen.queryByText('100')).not.toBeInTheDocument();
  });
});
