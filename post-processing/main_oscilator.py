import pandas as pd
import matplotlib.pyplot as plt
from sklearn.metrics import mean_squared_error

"LITTLE DTS"
dts = ['0.000100000', '0.000200000', '0.000300000', '0.000400000']

y_lims = {'beeman': [0.0002491712821944489, 0.0002496572314244125], 'gear': [
    0.0002491922367250463, 0.00024943732110377983], 'euler': [0.0002246100069899247, 0.0002406905542996367]}

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
    plt.scatter(list(map(lambda dt: float(dt), dts)), errors,
                label='Error cuadrático para ' + oscilator_type)
    plt.xlabel('Dt [S]', fontsize=16)
    plt.ylabel('Error cuadrático', fontsize=16)
    plt.xlim(-0, 0.0005)
    plt.ylim(y_lims[oscilator_type][0], y_lims[oscilator_type][1])
    plt.ticklabel_format(axis="x", style="sci",
                         scilimits=(-4, -4), useMathText=True)
    plt.ticklabel_format(axis="y", style="sci",
                         scilimits=(-4, -4), useMathText=True)
    plt.title('Error cuadrático para: ' + oscilator_type)
    plt.tight_layout()
    plt.show()
    errors_for_oscilators[oscilator_type] = errors

print('errors beeman', min(errors_for_oscilators['beeman']), max(errors_for_oscilators['beeman']))
print('errors gear', min(errors_for_oscilators['gear']), max(errors_for_oscilators['gear']))
print('errors euler', min(errors_for_oscilators['euler']), max(errors_for_oscilators['euler']))

"BIGGER DTS"

# dts = ['0.000000010', '0.000000100',
#        '0.000001000', '0.000010000', '0.000100000', '0.001000000', '0.010000000']

# y_lims = {'beeman': [0.00013, 0.00039], 'gear': [
#     0.000143, 0.000351], 'euler': [0.0000928, 0.00627]}

# oscilators = ['beeman', 'gear', 'euler']

# errors_for_oscilators = {'beeman': [],
#                          'gear': [],
#                          'euler': []}
# for oscilator_type in oscilators:
#     errors = []
#     for dt in dts:
#         oscilator = pd.read_csv(
#             f'/home/tomas/itba/ss-tp4/out/oscillator-analytic-{dt}.csv')
#         oscilator_analytics_position = oscilator['position']

#         oscilator_to_cmp = pd.read_csv(
#             f'/home/tomas/itba/ss-tp4/out/oscillator-{oscilator_type}-{dt}.csv')
#         oscilator_to_cmp_position = oscilator_to_cmp['position']
#         errors.append(mean_squared_error(
#             oscilator_analytics_position, oscilator_to_cmp_position))
#     plt.scatter(list(map(lambda dt: dt, dts)), errors,
#                 label='Error cuadrático para ' + oscilator_type)
#     # Create a figure instance
#     fig = plt.figure(1, figsize=(9, 6))

#     # Create an axes instance
#     ax = fig.add_subplot(111)

#     ## Custom x-axis labels
#     dts_lindos = ['0.001', '0.01',
#        '0.1', '1', '10', '100', '1000']
#     ax.set_xticklabels(dts_lindos)

#     plt.xlabel('Dt [S]', fontsize=16)
#     plt.ylabel('Error cuadrático', fontsize=16)
#     plt.title('Error cuadrático para: ' + oscilator_type)
#     # plt.xlim(0, 0.011)
#     # plt.yscale('log')
#     plt.ylim(0.0001, 0.008)
#     plt.ticklabel_format(axis="y", style="sci",
#                          scilimits=(-3, -3), useMathText=True)
#     plt.tight_layout()
#     plt.show()
#     errors_for_oscilators[oscilator_type] = errors

# print('errors beeman', errors_for_oscilators['beeman'])
# print('errors gear', errors_for_oscilators['gear'])
# # print('errors euler', errors_for_oscilators['euler'])
