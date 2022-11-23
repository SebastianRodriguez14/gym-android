package com.tecfit.gym_android.models.custom

import java.util.*

data class NotificationMembership(var notificationDisplayedThreeDaysBefore:Boolean, val
notificationDisplayedSameDay: Boolean) {}

/*
* notificationDisplayedThreeDaysBefore
* true = ya se mostró la notificación que aparece tres días antes
* false = aun no se ha mostrado
* notificationDisplayedSameDay
* true = ya se mostró la notificación que aparece el mismo día
* false = aun no se ha mostrado
* activeNotification
* true = está activado el switch para mostrar la notificación al finalizar la membresía
* false = no está activado*/