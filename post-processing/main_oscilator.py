import pandas as pd
import matplotlib.pyplot as plt
from sklearn.metrics import mean_squared_error

dts = ['0.0001', '0.0002', '0.0003', '0.0004', '0.0005', '0.0006', '0.0007', '0.0008',
       '0.0009', '0.0010']

oscilators = ['beeman', 'gear', 'euler']

errors_for_oscilators = {'beeman': [],
                         'gear': [],
                         'euler': []}
for oscilator_type in oscilators:
    errors = []
    for dt in dts:
        oscilator = pd.read_csv(
            f'/home/tomas/itba/ss-tp4/out/oscillator-analytic-{dt}.csv')
        oscilator_analytics_position = oscilator['position']

        oscilator_to_cmp = pd.read_csv(
            f'/home/tomas/itba/ss-tp4/out/oscillator-{oscilator_type}-{dt}.csv')
        oscilator_to_cmp_position = oscilator_to_cmp['position']

        errors.append(mean_squared_error(
            oscilator_analytics_position, oscilator_to_cmp_position))
    errors_for_oscilators[oscilator_type] = errors

print('errors beeman', errors_for_oscilators['beeman'])
print('errors gear', errors_for_oscilators['gear'])
print('errors euler', errors_for_oscilators['euler'])
