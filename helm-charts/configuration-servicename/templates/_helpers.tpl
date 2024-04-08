{{- define "rolebinding.name" -}}
{{- if .Values.config.version -}}
{{ .Chart.Name }}-rolebinding-{{ .Values.config.version }}
{{- else -}}
{{ .Chart.Name }}-rolebinding
{{- end -}}
{{- end -}}

{{- define "serviceaccount.name" -}}
{{- if .Values.config.version -}}
{{ .Values.serviceAccount.name }}-{{ .Values.config.version }}
{{- else -}}
{{ .Values.serviceAccount.name }}
{{- end -}}
{{- end -}}
